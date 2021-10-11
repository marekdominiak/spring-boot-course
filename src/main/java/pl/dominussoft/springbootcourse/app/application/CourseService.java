package pl.dominussoft.springbootcourse.app.application;

import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.Price;

import java.util.UUID;

@ApplicationService
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public UUID handle(CreateCourse cmd) {
        Price price = new Price(cmd.getAmount(), cmd.getCurrency());
        Course course = new Course(cmd.getTitle(), cmd.getDescription(), cmd.getDuration(), cmd.getKeywords(), price);
        final var saved = repository.save(course);
        return saved.getId();
    }

    public UUID handle(UpdateCourse cmd) {
        Course course = repository.findById(cmd.getId()).orElse(null);

        Price price = new Price(cmd.getAmount(), cmd.getCurrency());
        course.updatePrice(price);
        course.setTitle(cmd.getTitle());
        course.setDescription(cmd.getDescription());
        course.setKeywords(cmd.getKeywords());
        course.setDuration(cmd.getDuration());
        final var saved = repository.save(course);
        return saved.getId();
    }

    public void deleteCourse(UUID id) {
        repository.deleteById(id);
    }
}
