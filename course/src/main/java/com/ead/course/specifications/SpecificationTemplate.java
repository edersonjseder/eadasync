package com.ead.course.specifications;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {
    public static Specification<Module> moduleCourseId(final UUID id) {
        return ((root, query, cb) -> {
            query.distinct(true);
            Root<Module> module = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> courseModules = course.get("modules");
            return cb.and(cb.equal(course.get("id"), id), cb.isMember(module, courseModules));
        });
    }

    public static Specification<Lesson> lessonModuleId(final UUID id) {
        return ((root, query, cb) -> {
            query.distinct(true);
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");
            return cb.and(cb.equal(module.get("id"), id), cb.isMember(root, moduleLessons));
        });
    }

    public static Specification<Course> courseUserId(final UUID userId) {
        return ((root, query, cb) -> {
            query.distinct(true);
            Join<Course, CourseUser> courseProd = root.join("courseUsers");
            return cb.equal(courseProd.get("userId"), userId);
        });
    }
}
