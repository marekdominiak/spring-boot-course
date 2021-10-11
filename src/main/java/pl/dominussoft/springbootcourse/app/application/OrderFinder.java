package pl.dominussoft.springbootcourse.app.application;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pl.dominussoft.springbootcourse.app.domain.Order;
import pl.dominussoft.springbootcourse.app.domain.OrderRepository;

import java.util.List;
import java.util.UUID;


@Service
public class OrderFinder {

    private OrderRepository orderRepository;

    public OrderFinder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findOrder(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAll() {
        return Lists.newArrayList(orderRepository.findAll());
    }
}
