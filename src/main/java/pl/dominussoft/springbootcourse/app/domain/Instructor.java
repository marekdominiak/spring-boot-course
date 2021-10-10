package pl.dominussoft.springbootcourse.app.domain;

import lombok.Getter;
import lombok.ToString;
import pl.dominussoft.springbootcourse.app.domain.base.AggregateRoot;

import java.util.Set;

@ToString
public class Instructor extends AggregateRoot {

    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String bio;

    @Getter
    Set<String> keywords;

    public Instructor(String firstName, String lastName, String bio, Set<String> keywords) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.keywords = keywords;
    }

    // Instructor won't have a massive number of courses he provides, we can have them directly here
//    @Getter
//    private Set<Course> courses;

}
