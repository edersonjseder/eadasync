package com.ead.authuser.specifications;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    public static Specification<User> userCourseId(final UUID courseId) {
        return ((root, query, cb) -> {
            query.distinct(true);
            Join<User, UserCourse> userProd = root.join("userCourses");
            return cb.equal(userProd.get("courseId"), courseId);
        });
    }
}
