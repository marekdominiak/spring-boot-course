package pl.dominussoft.springbootcourse.app.domain;

import java.util.Set;
import java.util.UUID;

public final class InstructorBuilder {
    Set<String> keywords = Set.of("some", "keywords");
    private UUID id;
    private String firstName = "Rick";
    private String lastName = "R.";
    private String bio = "bio";
    private Integer age;

    private InstructorBuilder() {
    }

    public static InstructorBuilder anInstructor() {
        return new InstructorBuilder();
    }

    public InstructorBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public InstructorBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public InstructorBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public InstructorBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public InstructorBuilder withAge(Integer age) {
        this.age = age;
        return this;
    }

    public InstructorBuilder withKeywords(Set<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public Instructor build() {
        Instructor instructor = new Instructor(firstName, lastName, bio, age, keywords);
        instructor.setId(id);
        return instructor;
    }
}
