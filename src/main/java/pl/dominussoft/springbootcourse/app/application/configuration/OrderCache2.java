package pl.dominussoft.springbootcourse.app.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominussoft.springbootcourse.app.application.OrderFinder;
import pl.dominussoft.springbootcourse.app.domain.Order;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class OrderCache2 {

    @Autowired
    OrderFinder finder;

    List<Order> orders;

    @PostConstruct
    public void init() {
        orders = finder.findAll();
    }

    public List<Order> getOrders() {
        return orders;
    }
}
