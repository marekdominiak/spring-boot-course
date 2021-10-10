package pl.dominussoft.springbootcourse.app.domain;

import java.util.UUID;

public final class OrderLineBuilder {
    UUID courseId;
    String comment;
    Price price;
    private UUID id;

    private OrderLineBuilder() {
    }

    public static OrderLineBuilder anOrderLine() {
        return new OrderLineBuilder();
    }

    public OrderLineBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public OrderLineBuilder withCourseId(UUID courseId) {
        this.courseId = courseId;
        return this;
    }

    public OrderLineBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public OrderLineBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    public OrderLine build() {
        OrderLine orderLine = new OrderLine(courseId, comment, price);
        orderLine.setId(id);
        return orderLine;
    }
}
