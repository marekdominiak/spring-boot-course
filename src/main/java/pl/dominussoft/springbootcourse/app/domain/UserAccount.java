package pl.dominussoft.springbootcourse.app.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.dominussoft.springbootcourse.app.domain.base.AggregateRoot;

@ToString
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserAccount extends AggregateRoot {

    String firstName;
    String lastName;
    String email;
    String password;
    boolean enabled = true;
    Role role;

    public UserAccount(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
