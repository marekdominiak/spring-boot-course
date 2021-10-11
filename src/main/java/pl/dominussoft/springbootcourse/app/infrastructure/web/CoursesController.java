package pl.dominussoft.springbootcourse.app.infrastructure.web;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dominussoft.springbootcourse.app.application.CourseService;
import pl.dominussoft.springbootcourse.app.application.CreateCourse;
import pl.dominussoft.springbootcourse.app.application.UpdateCourse;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CoursesController {

    public static final String BASE_URL = "/courses/";

    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CoursesController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    public void postWithRequestMapping(@RequestBody CreateCourseRequest request) {
        saveCourse(request, request.getPrice());
    }

    public void postWithPostMapping(@RequestBody CreateCourseRequest request) {
        saveCourse(request, request.getPrice());
    }

    public void postWithCustomResponseStatus(@RequestBody CreateCourseRequest request) {
        saveCourse(request, request.getPrice());
    }

    public CourseModel getWithRequestMapping(UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResourceNotFoundException("Course " + id + " not found");
        }
    }

    public CourseModel getWithGetMapping(UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResourceNotFoundException("Course " + id + " not found");
        }
    }

    public CourseModel putWithPutMapping(
            UpdateCourseRequest request,
            UUID id) {
        Price price = request.getPrice();
        courseService.handle(UpdateCourse.builder()
                .id(id)
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .keywords(request.getKeywords())
                .amount(price.getAmount())
                .currency(Currency.valueOf(price.getCurrency()))
                .build());
        return get(id);
    }

    public void deleteCourse(UUID id) {
        courseService.deleteCourse(id);
    }


    public CourseModel getWithRequestParam(UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResourceNotFoundException("Course " + courseId + " not found");
        }
    }

    public CourseModel putWithHeaders(
            UpdateCourseRequest updateCourseRequest,
            UUID id) {
        Price price = updateCourseRequest.getPrice();
        courseService.handle(UpdateCourse.builder()
                .id(id)
                .title(updateCourseRequest.getTitle())
                .description(updateCourseRequest.getDescription())
                .duration(updateCourseRequest.getDuration())
                .keywords(updateCourseRequest.getKeywords())
                .amount(price.getAmount())
                .currency(Currency.valueOf(price.getCurrency()))
                .build());

        // add header (ourOwnHeader) from request to response

        return get(id);
    }


    public void postWithValidation(CreateCourseRequest request) {
        saveCourse(request, request.getPrice());
    }


    @PostMapping(value = BASE_URL, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HttpEntity<CourseModel> post(@RequestBody CreateCourseRequest request) {
        Price price = request.getPrice();
        UUID id = saveCourse(request, price);

        CourseModel createdCourse = get(id);
        Link link = createdCourse.getLink(IanaLinkRelations.SELF).orElseThrow();
        // for discussion
        return ResponseEntity.created(link.toUri()).body(createdCourse);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = BASE_URL + "{id}", produces = APPLICATION_JSON_VALUE)
    public CourseModel get(@PathVariable UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResourceNotFoundException("Course " + id + " not found");
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

    private UUID saveCourse(CreateCourseRequest request, Price price) {
        return courseService.handle(CreateCourse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .keywords(request.getKeywords())
                .amount(price.getAmount())
                .currency(Currency.valueOf(price.getCurrency()))
                .build());
    }
}
