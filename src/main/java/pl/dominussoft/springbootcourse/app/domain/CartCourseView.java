package pl.dominussoft.springbootcourse.app.domain;

import lombok.Value;

@Value
public class CartCourseView {
    String title;
    String courseId;
    String cartId;
}
