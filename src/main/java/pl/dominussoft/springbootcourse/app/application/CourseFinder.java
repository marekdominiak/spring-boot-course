package pl.dominussoft.springbootcourse.app.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Component
public class CourseFinder {

    private CourseRepository courseRepository;

    public CourseFinder(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findCourseBy(UUID id) {
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

}
