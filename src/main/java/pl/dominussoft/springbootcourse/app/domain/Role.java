package pl.dominussoft.springbootcourse.app.domain;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {

    STUDENT(Set.of(Authority.BUY_COURSE)),
    ADMIN(Set.of(Authority.values()));

    private Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
