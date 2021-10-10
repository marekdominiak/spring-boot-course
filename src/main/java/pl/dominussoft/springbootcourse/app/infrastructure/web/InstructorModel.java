package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.jackson.Jacksonized;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

@Jacksonized
@EqualsAndHashCode(callSuper = true)
@Value
@NonFinal // because Hateos links wouldn't generate the class if onluy @Value was presented
public class InstructorModel extends RepresentationModel<InstructorModel> {
    UUID id;
    String firstName;
    String lastName;
    String bio;
    Set<String> keywords;
}
