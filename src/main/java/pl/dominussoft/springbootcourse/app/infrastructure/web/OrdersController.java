package pl.dominussoft.springbootcourse.app.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.dominussoft.springbootcourse.app.application.PlaceOrder;
import pl.dominussoft.springbootcourse.app.application.PlaceOrderService;
import pl.dominussoft.springbootcourse.app.infrastructure.security.CustomUser;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class OrdersController {

    public static final String BASE_URL = "/purchase/";

    private final PlaceOrderService handler;
//    private final OrderFinder finder;

    public OrdersController(PlaceOrderService handler) {
        this.handler = handler;
    }

//    @Autowired - you really don't need it
//    public OrdersController(PlaceOrderService handler, OrderFinder finder) {
//        this.handler = handler;
//        this.finder = finder;
//    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = BASE_URL, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void placeOrder(@RequestBody PlaceOrderRequest request,
                           @AuthenticationPrincipal CustomUser user) {
        handler.placeOrder(new PlaceOrder(user.getId()));
    }
}
