package pl.dominussoft.springbootcourse.app.application;

import org.springframework.stereotype.Service;
import pl.dominussoft.springbootcourse.app.domain.Order;
import pl.dominussoft.springbootcourse.app.domain.OrderRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderFinder {

    final OrderRepository orderRepository;

    public OrderFinder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> findOrder(UUID id) {
        return orderRepository.findById(id);
    }
}
