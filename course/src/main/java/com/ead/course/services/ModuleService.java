package com.ead.course.services;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.exceptions.ModuleException;
import com.ead.course.exceptions.ModuleNotFoundException;
import com.ead.course.models.Module;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.utils.ModuleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.ead.course.constants.ModuleMessagesConstants.MODULE_TITLE_EXISTENTE_MENSAGEM;

@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseService courseService;
    private final ModuleUtils moduleUtils;

    public Page<ModuleDto> findAllByCourse(Specification<Module> spec, Pageable pageable) {
        return moduleUtils.toListModuleDto(moduleRepository.findAll(spec, pageable));
    }

    public Module getOneModule(UUID courseId, UUID id) {
        return moduleRepository.findModuleIntoCourse(courseId, id)
                .orElseThrow(() -> new ModuleNotFoundException(courseId, id));
    }

    public Module fetchModuleById(UUID id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ModuleNotFoundException(id));
    }

    public ModuleDto saveModule(UUID courseId, ModuleDto moduleDto) {
        Module module;

        if (moduleDto.getId() == null) {
            if (moduleRepository.existsModuleByTitle(moduleDto.getTitle())) {
                throw new ModuleException(MODULE_TITLE_EXISTENTE_MENSAGEM + moduleDto.getTitle());
            }

            var course = courseService.getOneCourse(courseId);

            module = new Module();

            BeanUtils.copyProperties(moduleDto, module);

            module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            module.setCourse(course);

        } else {
            module = moduleRepository.findModuleIntoCourse(courseId, moduleDto.getId())
                    .orElseThrow(() -> new ModuleNotFoundException(courseId, moduleDto.getId()));

            module.setTitle(moduleDto.getTitle());
            module.setDescription(moduleDto.getDescription());
        }

        return moduleUtils.toModuleDto(moduleRepository.save(module));
    }

    @Transactional
    public void removeModule(UUID courseId, UUID id) {
        var module = moduleRepository.findModuleIntoCourse(courseId, id)
                .orElseThrow(() -> new ModuleNotFoundException(courseId, id));

        var lessons = lessonRepository.findAllLessonsIntoModule(id);
        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.delete(module);
    }
}
