package pl.dominussoft.springbootcourse.app.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class CartBuilder {
    UUID userId = UUID.randomUUID();
    Set<UUID> courses = new HashSet<>();
    private UUID id;

    private CartBuilder() {
    }

    public static CartBuilder aCart() {
        return new CartBuilder();
    }

    public CartBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public CartBuilder withUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public CartBuilder withCourses(Set<UUID> courses) {
        this.courses = courses;
        return this;
    }

    public Cart build() {
        Cart cart = new Cart(userId, courses);
        cart.setId(id);
        return cart;
    }
}
