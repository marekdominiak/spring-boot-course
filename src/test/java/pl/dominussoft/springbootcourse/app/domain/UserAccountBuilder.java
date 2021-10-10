package pl.dominussoft.springbootcourse.app.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public final class UserAccountBuilder {
    String firstName = "admin";
    String lastName = "admin";
    String email = "Admin@ecourses.pl";
    String password = "admin";
    boolean enabled = true;
    boolean wrongPassword = false;
    Role role = Role.ADMIN;
    private UUID id;

    private UserAccountBuilder() {
    }

    public static UserAccountBuilder anAdmin() {
        return new UserAccountBuilder();
    }

    public static UserAccountBuilder aStudent() {
        return new UserAccountBuilder()
                .withFirstName("Student")
                .withLastName("Student")
                .withEmail("student@ecourses.pl")
                .withPassword("student")
                .withRole(Role.STUDENT);
    }

    public UserAccountBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public UserAccountBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserAccountBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserAccountBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserAccountBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserAccountBuilder withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserAccountBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public UserAccount build() {
        UserAccount userAccount = new UserAccount(firstName, lastName, email, password, enabled, role);
        userAccount.setId(id);
        return userAccount;
    }

    public UserAccountBuilder withWrongPassword() {
        this.wrongPassword = true;
        return this;
    }
}
