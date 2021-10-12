package pl.dominussoft.springbootcourse.app.domain;

import lombok.Value;

@Value
public class CourseAddedToCartEvent {
    Course course;
    UserAccount user;
}
