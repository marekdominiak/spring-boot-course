package pl.dominussoft.springbootcourse.app.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dominussoft.springbootcourse.app.domain.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AddCourseToCartService {

    private final CourseRepository courseRepository;
    private final UserAccountRepository userAccountRepository;
    private final CartRepository cartRepository;

    public AddCourseToCartService(CourseRepository courseRepository, UserAccountRepository userAccountRepository, CartRepository cartRepository) {
        this.courseRepository = courseRepository;
        this.userAccountRepository = userAccountRepository;
        this.cartRepository = cartRepository;
    }

    public UUID handle(AddCourseToCart cmd) {
        Course course = courseRepository.findById(cmd.getCourseId()).orElseThrow();
        UserAccount userAccount = userAccountRepository.findById(cmd.getUserId()).orElseThrow();
        Optional<Cart> cartOpt = cartRepository.findByUserId(userAccount);

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.add(course);
            Cart saved = cartRepository.save(cart);
            return saved.getId();
        } else {
            Cart saved = cartRepository.save(new Cart(userAccount.getId(), Set.of(course.getId())));
            return saved.getId();
        }
    }
}
