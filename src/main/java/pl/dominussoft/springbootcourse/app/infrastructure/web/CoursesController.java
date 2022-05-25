package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.dominussoft.springbootcourse.app.application.CreateCourse;
import pl.dominussoft.springbootcourse.app.application.CreateCourseService;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class CoursesController {

    public static final String BASE_URL = "/courses/";
    private final CreateCourseService createCourseService;
    private final CourseRepository courseRepository;

    @PostMapping(value = BASE_URL, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HttpEntity<CourseModel> post(@RequestBody CreateCourseRequest request) {
        Price price = request.getPrice();
        UUID id = createCourseService.handle(CreateCourse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .keywords(request.getKeywords())
                .amount(price.getAmount())
                .currency(Currency.valueOf(price.getCurrency()))
                .build());

        CourseModel createdCourse = get(id);
        Link link = createdCourse.getLink(IanaLinkRelations.SELF).orElseThrow();
        return ResponseEntity.created(link.toUri()).body(createdCourse);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = BASE_URL + "{id}", produces = APPLICATION_JSON_VALUE)
    public CourseModel get(@PathVariable UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course " + id + " not found");
        }
    }

    @GetMapping(value = BASE_URL, produces = APPLICATION_JSON_VALUE)
    public List<CourseModel> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseModel> list = new ArrayList<>();
        for (Course course : courses) {
            CourseModel courseModel = toModel(course);
            list.add(courseModel);
        }
        return list;
    }

    private CourseModel toModel(Course course) {
        CourseModel model = new CourseModel(course.getId(), course.getTitle(), course.getDescription(), course.getDuration(), course.getKeywords());
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CoursesController.class).get(model.getId())).withSelfRel());
        return model;
    }
}