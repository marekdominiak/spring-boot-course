package pl.dominussoft.springbootcourse.app.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.dominussoft.springbootcourse.app.domain.base.AggregateRoot;

import java.util.Set;
import java.util.UUID;

@ToString
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Cart extends AggregateRoot {

    UUID userId;
    Set<UUID> courses;

    public void add(Course course) {
        courses.add(course.getId());
    }
}
