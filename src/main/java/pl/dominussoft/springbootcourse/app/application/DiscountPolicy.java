package pl.dominussoft.springbootcourse.app.application;


import pl.dominussoft.springbootcourse.app.domain.Order;
import pl.dominussoft.springbootcourse.app.domain.OrderRepository;
import pl.dominussoft.springbootcourse.app.domain.UserAccount;

import java.util.List;

public class DiscountPolicy {

    int minimumOrders;
    int discount;
    OrderRepository orderRepository;

    public DiscountPolicy(int minimumOrders, int discount, OrderRepository orderRepository) {
        this.minimumOrders = minimumOrders;
        this.discount = discount;
        this.orderRepository = orderRepository;
    }

    public int discountPercent(UserAccount userAccount) {
        List<Order> orders = orderRepository.findByUserId(userAccount.getId());
        if (orders.size() >= minimumOrders) {
            return discount;
        }
        return 0;
    }
}
