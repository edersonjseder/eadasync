package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.ModuleSpec;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.utils.ModuleUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    private final ModuleUtils moduleUtils;

    @GetMapping(value = "/all/course/{courseId}/modules")
    public ResponseEntity<Page<ModuleDto>> getAllModules(@PathVariable("courseId") UUID courseId,
                                                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, ModuleSpec spec) {
        var modulePage = moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);

        // Hateoas link creation snippet
        if (!modulePage.isEmpty()) {
            modulePage.toList().forEach((module) -> {
                module.add(linkTo(methodOn(ModuleController.class).getModuleById(courseId, module.getId())).withSelfRel());
            });
        }

        return ResponseEntity.ok(modulePage);
    }

    @GetMapping("/course/{courseId}/modules/{id}")
    public ResponseEntity<ModuleDto> getModuleById(@PathVariable("courseId") UUID courseId, @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(moduleUtils.toModuleDto(moduleService.getOneModule(courseId, id)));
    }

    @PostMapping(value = "/register/course/{courseId}/modules")
    public ResponseEntity<ModuleDto> registerModule(@PathVariable("courseId") UUID courseId, @RequestBody @Valid ModuleDto moduleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.saveModule(courseId, moduleDto));
    }

    @PutMapping(value = "/update/course/{courseId}/modules")
    public ResponseEntity<ModuleDto> updateModule(@PathVariable("courseId") UUID courseId, @RequestBody ModuleDto moduleDto) {
        return ResponseEntity.ok(moduleService.saveModule(courseId, moduleDto));
    }

    @DeleteMapping(value = "/remove/course/{courseId}/module/{id}")
    public ResponseEntity<String> removeModule(@PathVariable("courseId") UUID courseId, @PathVariable("id") UUID id) {
        moduleService.removeModule(courseId, id);
        return ResponseEntity.ok("Module successfully removed.");
    }
}
