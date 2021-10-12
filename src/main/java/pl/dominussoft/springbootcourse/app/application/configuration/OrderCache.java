package pl.dominussoft.springbootcourse.app.application.configuration;

import pl.dominussoft.springbootcourse.app.domain.Order;

import java.util.List;

public class OrderCache {

    List<Order> orders;

    public OrderCache(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
