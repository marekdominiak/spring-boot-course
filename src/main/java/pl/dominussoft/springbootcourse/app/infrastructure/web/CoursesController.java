package pl.dominussoft.springbootcourse.app.infrastructure.web;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.dominussoft.springbootcourse.app.application.CourseService;
import pl.dominussoft.springbootcourse.app.application.CreateCourse;
import pl.dominussoft.springbootcourse.app.application.UpdateCourse;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.Currency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class CoursesController {

    public static final String BASE_URL = "/courses/";

    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CoursesController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @RequestMapping(value = "/courses/postWithRequestMapping",
            method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void postWithRequestMapping(@RequestBody CreateCourseRequest request) {
        saveCourse(request, request.getPrice());
    }

    @PostMapping(value = "/courses/postWithPostMapping", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void postWithPostMapping(@RequestBody CreateCourseRequest request) {
        saveCourse(request, request.getPrice());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/courses/postWithCustomResponseStatus", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void postWithCustomResponseStatus(@RequestBody CreateCourseRequest request) {
        saveCourse(request, request.getPrice());
    }


    @RequestMapping(value = "/courses/getWithRequestMapping/{id}",
            method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public CourseModel getWithRequestMapping(@PathVariable UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course " + id + " not found");
        }
    }

    @GetMapping(value = "/courses/getWithGetMapping/{id}", produces = APPLICATION_JSON_VALUE)
    public CourseModel getWithGetMapping(@PathVariable UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course " + id + " not found");
        }
    }


    @PutMapping(value = "/courses/putWithPutMapping/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CourseModel putWithPutMapping(
            @RequestBody UpdateCourseRequest request,
            @PathVariable UUID id) {
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

    @DeleteMapping(value = BASE_URL + "{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
    }


    @GetMapping(value = "/courses/getWithRequestParam", produces = APPLICATION_JSON_VALUE)
    public CourseModel getWithRequestParam(@RequestParam UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            return toModel(courseOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course " + courseId + " not found");
        }
    }

    @PutMapping(value = "/courses/putWithHeaders/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CourseModel putWithHeaders(
            @RequestBody UpdateCourseRequest updateCourseRequest,
            @PathVariable UUID id,
            @RequestHeader String ourOwnHeader,
            HttpServletRequest request,
            HttpServletResponse response) {
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


        String value = request.getHeader("ourOwnHeader");
        response.addHeader("ourOwnHeader", value);

        return get(id);
    }


    @PostMapping(value = BASE_URL + "postWithValidation", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void postWithValidation(@RequestBody @Valid CreateCourseRequest request) {
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