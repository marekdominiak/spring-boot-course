package pl.dominussoft.springbootcourse.app.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {

    Optional<Course> findById(UUID courseId);

    Course save(Course course);

    List<Course> findAll();
}
