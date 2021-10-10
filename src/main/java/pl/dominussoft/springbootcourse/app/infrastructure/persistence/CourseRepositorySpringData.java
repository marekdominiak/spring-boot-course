package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;

import java.util.UUID;

@Repository
public interface CourseRepositorySpringData extends CourseRepository, CrudRepository<Course, UUID> {
}
