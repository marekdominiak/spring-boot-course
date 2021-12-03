package pl.dominussoft.springbootcourse.app.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dominussoft.springbootcourse.app.domain.Cart;
import pl.dominussoft.springbootcourse.app.domain.CartRepository;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseAddedToCartEvent;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.UserAccount;
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AddCourseToCartService {

    private final CourseRepository courseRepository;
    private final UserAccountRepository userAccountRepository;
    private final CartRepository cartRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AddCourseToCartService(CourseRepository courseRepository, UserAccountRepository userAccountRepository, CartRepository cartRepository, ApplicationEventPublisher eventPublisher) {
        this.courseRepository = courseRepository;
        this.userAccountRepository = userAccountRepository;
        this.cartRepository = cartRepository;
        this.eventPublisher = eventPublisher;
    }

    public UUID handle(AddCourseToCart cmd) {
        Course course = courseRepository.findById(cmd.getCourseId()).orElseThrow();
        UserAccount userAccount = userAccountRepository.findById(cmd.getUserId()).orElseThrow();
        Optional<Cart> cartOpt = cartRepository.findByUserId(userAccount);

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.add(course);
            Cart saved = cartRepository.save(cart);
            eventPublisher.publishEvent(new CourseAddedToCartEvent(course, userAccount)); // @EventListener <---
            return saved.getId();
        } else {
            Cart saved = cartRepository.save(new Cart(userAccount.getId(), Set.of(course.getId())));
            eventPublisher.publishEvent(new CourseAddedToCartEvent(course, userAccount)); // @EventListener <---
            return saved.getId();
        }
    }
}
