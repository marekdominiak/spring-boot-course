package pl.dominussoft.springbootcourse.app.application;

import lombok.RequiredArgsConstructor;
import pl.dominussoft.springbootcourse.app.domain.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@ApplicationService
@RequiredArgsConstructor
public class PlaceOrderService {

    private final CourseRepository courseRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public void placeOrder(PlaceOrder cmd) {
        Cart cart = cartRepository.findByUserId(cmd.getUserId()).orElseThrow();

        Set<UUID> courses = cart.getCourses();
        for (UUID courseId : courses) {
            Course course = courseRepository.findById(courseId).orElseThrow();
            Order order = new Order(cmd.getUserId(), LocalDate.now());
            // TODO discount calculation
            order.addLine(new OrderLine(courseId, "For " + course.getTitle(), course.getPrice()));
            orderRepository.save(order);
//            publish event
        }
    }
}
