package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Jacksonized
@Valid
@Value
public class UpdateCourseRequest {
    @NotBlank
    String title;

    @NotBlank
    String description;

    @NotBlank
    String duration;

    Set<String> keywords;

    @Valid
    Price price;

    // Question: what happens in the code base when someone has added a new field here? How does it influence tests?
    // How to construct objects so we don't have to bother about that?
}
