package com.ead.course.services;

import com.ead.course.dtos.LessonDto;
import com.ead.course.exceptions.LessonNotFoundException;
import com.ead.course.exceptions.ModuleException;
import com.ead.course.exceptions.ModuleNotFoundException;
import com.ead.course.models.Lesson;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.utils.LessonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.ead.course.constants.LessonMessagesConstants.LESSON_TITLE_EXISTENTE_MENSAGEM;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;
    private final LessonUtils lessonUtils;

    public Page<LessonDto> findAllByModule(Specification<Lesson> spec, Pageable pageable) {
        return lessonUtils.toListLessonDto(lessonRepository.findAll(spec, pageable));
    }

    public Lesson getOneLesson(UUID moduleId, UUID id) {
        return lessonRepository.findLessonIntoModule(moduleId, id)
                .orElseThrow(() -> new LessonNotFoundException(moduleId, id));
    }

    public LessonDto saveLesson(UUID moduleId, LessonDto lessonDto) {
        Lesson lesson;

        if (lessonDto.getId() == null) {
            if (lessonRepository.existsLessonByTitle(lessonDto.getTitle())) {
                throw new ModuleException(LESSON_TITLE_EXISTENTE_MENSAGEM + lessonDto.getTitle());
            }

            var module = moduleService.fetchModuleById(moduleId);

            lesson = new Lesson();

            BeanUtils.copyProperties(lessonDto, lesson);

            lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            lesson.setModule(module);

        } else {
            lesson = lessonRepository.findLessonIntoModule(moduleId, lessonDto.getId())
                    .orElseThrow(() -> new LessonNotFoundException(moduleId, lessonDto.getId()));

            lesson.setTitle(lessonDto.getTitle());
            lesson.setDescription(lessonDto.getDescription());
            lesson.setVideoUrl(lessonDto.getVideoUrl());
        }

        return lessonUtils.toLessonDto(lessonRepository.save(lesson));
    }

    public void removeLesson(UUID moduleId, UUID id) {
        var lesson = lessonRepository.findLessonIntoModule(moduleId, id)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId, id));
        lessonRepository.delete(lesson);
    }
}
