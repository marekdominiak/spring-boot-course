package pl.dominussoft.springbootcourse.app.application;

import pl.dominussoft.springbootcourse.app.domain.UserAccount;

public interface Context {
    UserAccount loggedInUser();
}
