package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Jacksonized
@Value
public class CreateCourseRequest {
    String title;

    String description;

    String duration;

    Set<String> keywords;

    Price price;

    // Question: what happens in the code base when someone has added a new field here? How does it influence tests?
    // How to construct objects so we don't have to bother about that?
}
