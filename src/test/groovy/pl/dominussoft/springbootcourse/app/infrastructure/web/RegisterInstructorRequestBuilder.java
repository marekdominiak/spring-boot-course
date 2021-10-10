package pl.dominussoft.springbootcourse.app.infrastructure.web;

import java.util.HashSet;
import java.util.Set;

public final class RegisterInstructorRequestBuilder {
    String firstName = "Rick";
    String lastName = "R.";
    String bio = "He will never let you down.";
    Set<String> keywords = new HashSet<>();

    private RegisterInstructorRequestBuilder() {
    }

    public static RegisterInstructorRequestBuilder aRegisterInstructorRequest() {
        return new RegisterInstructorRequestBuilder();
    }

    public RegisterInstructorRequestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public RegisterInstructorRequestBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RegisterInstructorRequestBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public RegisterInstructorRequestBuilder withKeywords(Set<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public RegisterInstructorRequest build() {
        return new RegisterInstructorRequest(firstName, lastName, bio, keywords);
    }
}
