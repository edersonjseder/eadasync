package com.ead.course.repositories;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {
    boolean existsByCourseAndUserId(Course course, UUID userId);

    boolean existsByUserId(UUID userId);

    @Query(value = "select * from tb_courses_users where course_id = :courseId", nativeQuery = true)
    List<CourseUser> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);

    void deleteAllByUserId(UUID userId);
}
