package pl.dominussoft.springbootcourse.app.application;

import lombok.RequiredArgsConstructor;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.Price;

import java.util.UUID;

@ApplicationService
@RequiredArgsConstructor
public class CreateCourseService {

    private final CourseRepository repository;

    public UUID handle(CreateCourse cmd) {
        Price price = new Price(cmd.getAmount(), cmd.getCurrency());
        Course course = new Course(cmd.getTitle(), cmd.getDescription(), cmd.getDuration(), cmd.getKeywords(), price);
        final var saved = repository.save(course);
        return saved.getId();
    }
}
