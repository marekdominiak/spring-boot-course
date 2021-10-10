package pl.dominussoft.springbootcourse.app.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class OrderBuilder {
    UUID id = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    LocalDate orderedAt = LocalDate.now();
    Set<OrderLine> lines = new HashSet<>();

    private OrderBuilder() {
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public OrderBuilder withUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder withOrderedAt(LocalDate orderedAt) {
        this.orderedAt = orderedAt;
        return this;
    }

    public OrderBuilder withLines(Set<OrderLine> lines) {
        this.lines = lines;
        return this;
    }

    public OrderBuilder addLine(OrderLine orderLine) {
        this.lines.add(orderLine);
        return this;
    }

    public Order build() {
        Order order = new Order(userId, orderedAt, lines);
        order.setId(id);
        return order;
    }
}
