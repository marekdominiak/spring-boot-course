package pl.dominussoft.springbootcourse.app.application;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Builder
@Value
public class RegisterInstructor implements Command {
    String firstName;
    String lastName;
    String bio;
    Set<String> keywords;
}
