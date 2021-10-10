package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Value;

import java.util.Set;

@Value
public class RegisterInstructorRequest {
    String firstName;
    String lastName;
    String bio;
    Set<String> keywords;
}
