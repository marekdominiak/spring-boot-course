package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Value
public class CreateCourseRequest {
    @NotNull
    String title;
    @NotNull
    String description;

    String duration;

    Set<String> keywords;
    @NotNull
    Price price;

    // Question: what happens in the code base when someone has added a new field here? How does it influence tests?
    // How to construct objects so we don't have to bother about that?
}
