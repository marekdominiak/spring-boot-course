package pl.dominussoft.springbootcourse.app.application;

import lombok.Value;

import java.util.UUID;

@Value
public class AddCourseToCart implements Command {
    UUID courseId;
    UUID userId;
}
