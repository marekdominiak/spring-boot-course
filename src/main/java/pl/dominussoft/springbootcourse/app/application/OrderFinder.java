package pl.dominussoft.springbootcourse.app.application;

import pl.dominussoft.springbootcourse.app.domain.Order;
import pl.dominussoft.springbootcourse.app.domain.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderFinder {

    private final OrderRepository orderRepository;

    public OrderFinder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findOrder(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAll() {
        throw new RuntimeException("To be implemented");
    }
}
