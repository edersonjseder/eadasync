package com.ead.course.controllers;

import com.ead.course.dtos.LessonDto;
import com.ead.course.services.LessonService;
import com.ead.course.specifications.LessonSpec;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.utils.LessonUtils;
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
public class LessonController {
    private final LessonService lessonService;
    private final LessonUtils lessonUtils;

    @GetMapping(value = "/all/module/{moduleId}/lessons")
    public ResponseEntity<Page<LessonDto>> getAllLessons(@PathVariable("moduleId") UUID moduleId,
                                                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, LessonSpec spec) {
        var lessonsPage = lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable);

        // Hateoas link creation snippet
        if (!lessonsPage.isEmpty()) {
            lessonsPage.toList().forEach((lesson) -> {
                lesson.add(linkTo(methodOn(LessonController.class).getLessonById(moduleId, lesson.getId())).withSelfRel());
            });
        }

        return ResponseEntity.ok(lessonsPage);
    }

    @GetMapping("/module/{moduleId}/lessons/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable("moduleId") UUID moduleId, @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(lessonUtils.toLessonDto(lessonService.getOneLesson(moduleId, id)));
    }

    @PostMapping(value = "/register/module/{moduleId}/lessons")
    public ResponseEntity<LessonDto> registerLesson(@PathVariable("moduleId") UUID moduleId, @RequestBody @Valid LessonDto lessonDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.saveLesson(moduleId, lessonDto));
    }

    @PutMapping(value = "/update/module/{moduleId}/lessons")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable("moduleId") UUID moduleId, @RequestBody @Valid LessonDto lessonDto) {
        return ResponseEntity.ok(lessonService.saveLesson(moduleId, lessonDto));
    }

    @DeleteMapping(value = "/remove/module/{moduleId}/lessons/{id}")
    public ResponseEntity<String> removeLesson(@PathVariable("moduleId") UUID moduleId, @PathVariable("id") UUID id) {
        lessonService.removeLesson(moduleId, id);
        return ResponseEntity.ok("Lesson successfully removed.");
    }
}
