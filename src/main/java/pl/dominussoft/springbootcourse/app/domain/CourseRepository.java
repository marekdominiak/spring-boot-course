package pl.dominussoft.springbootcourse.app.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends CrudRepository<Course, UUID> {

    Optional<Course> findById(UUID courseId);

    Course save(Course course);

    List<Course> findAll();
}
