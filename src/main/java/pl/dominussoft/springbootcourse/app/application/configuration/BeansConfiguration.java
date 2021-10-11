package pl.dominussoft.springbootcourse.app.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dominussoft.springbootcourse.app.application.OrderFinder;
import pl.dominussoft.springbootcourse.app.domain.OrderRepository;

@Configuration
public class BeansConfiguration {


    @Bean
    OrderFinder orderFinder(OrderRepository orderRepository) {
        return new OrderFinder(orderRepository);
    }

}
