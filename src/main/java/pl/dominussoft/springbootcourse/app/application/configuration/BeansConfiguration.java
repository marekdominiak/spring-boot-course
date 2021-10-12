package pl.dominussoft.springbootcourse.app.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dominussoft.springbootcourse.app.application.DiscountPolicy;
import pl.dominussoft.springbootcourse.app.application.OrderFinder;
import pl.dominussoft.springbootcourse.app.domain.Order;
import pl.dominussoft.springbootcourse.app.domain.OrderRepository;

import java.util.List;

@Configuration
public class BeansConfiguration {

    @Bean
    OrderFinder orderFinder(OrderRepository orderRepository) {
        return new OrderFinder(orderRepository);
    }

    @Bean
    DiscountPolicy discountPolicy(
            @Value("${discount.policy.discount:0}") int discount,
            @Value("${discount.policy.minumumOrders:10}") int minimumOrders,
            OrderRepository orderRepository) {
        return new DiscountPolicy(minimumOrders, discount, orderRepository);
    }


    @Bean
    OrderCache orderCache(OrderFinder orderFinder) {
        List<Order> orders = orderFinder.findAll();
        return new OrderCache(orders);
    }

}
