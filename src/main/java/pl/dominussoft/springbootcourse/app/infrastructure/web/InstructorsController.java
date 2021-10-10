package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dominussoft.springbootcourse.app.application.RegisterInstructor;
import pl.dominussoft.springbootcourse.app.application.RegisterInstructorService;
import pl.dominussoft.springbootcourse.app.domain.Instructor;
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class InstructorsController {

    private final RegisterInstructorService registerInstructorService;
    private final PagedResourcesAssembler<InstructorModel> pagedResourcesAssembler;
    private final InstructorRepository instructorRepository;

    //  @ResponseStatus(HttpStatus.CREATED) -> Why not?
    @PostMapping(value = "/instructors", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HttpEntity<InstructorModel> registerInstructor(@RequestBody RegisterInstructorRequest request) {
        UUID id = registerInstructorService.registerInstructor(RegisterInstructor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .bio(request.getBio())
                .keywords(request.getKeywords())
                .build());

        InstructorModel registeredInstructor = findById(id);
        Link link = registeredInstructor.getLink(IanaLinkRelations.SELF).orElseThrow();
        return ResponseEntity.created(link.toUri()).body(registeredInstructor);
    }

    @GetMapping(value = "/instructors/{id}", produces = APPLICATION_JSON_VALUE)
    public InstructorModel get(@PathVariable UUID id) {
        return findById(id);
    }

    private InstructorModel findById(UUID id) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(id);
        if (instructorOptional.isPresent()) {
            return toModel(instructorOptional.get());
        } else {
            throw new ResourceNotFoundException("Instructor resource with " + id + " wasn't found");
        }
    }

    private InstructorModel toModel(Instructor instructor) {
        InstructorModel model = new InstructorModel(instructor.getId(), instructor.getFirstName(), instructor.getLastName(),
                instructor.getBio(), instructor.getKeywords());
        model.add(WebMvcLinkBuilder.linkTo(methodOn(InstructorsController.class).get(instructor.getId())).withSelfRel());
        return model;
    }

    @GetMapping(value = "/instructors", produces = APPLICATION_JSON_VALUE)
    public PagedModel<InstructorModel> getAll(
            @PageableDefault(size = 10, page = 0, sort = "firstName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Instructor> instructorPage = instructorRepository.findAll(pageable);
        Page<InstructorModel> page = instructorPage.map(instructor -> toModel(instructor));
        return pagedResourcesAssembler.toModel(page, entity -> entity);
    }
}
