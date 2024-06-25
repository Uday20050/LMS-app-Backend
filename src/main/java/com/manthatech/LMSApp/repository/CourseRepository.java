package com.manthatech.LMSApp.repository;
import com.manthatech.LMSApp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseName(Course courseName);
    Set<Course> findByCourseNameIn(Set<String> courseNames);
}

