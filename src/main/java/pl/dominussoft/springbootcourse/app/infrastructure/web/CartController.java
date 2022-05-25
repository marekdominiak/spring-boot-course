package pl.dominussoft.springbootcourse.app.infrastructure.web;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.dominussoft.springbootcourse.app.application.AddCourseToCart;
import pl.dominussoft.springbootcourse.app.application.AddCourseToCartService;
import pl.dominussoft.springbootcourse.app.domain.Cart;
import pl.dominussoft.springbootcourse.app.domain.CartRepository;
import pl.dominussoft.springbootcourse.app.infrastructure.security.CustomUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class CartController {

    public static final String BASE_URL = "/cart/";

    private final AddCourseToCartService addCourseToCartService;
    private final CartRepository cartRepository;
//    private final Context context;

    @PostMapping(value = BASE_URL, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HttpEntity<CartModel> addCourseToCart(@RequestBody AddCourseToCartRequest request,
                                                 @AuthenticationPrincipal CustomUser user) {

        UUID loggedInUser = user.getId(); // Discuss both alternatives  UUID loggedInUser = context.loggedInUser().getId();
        UUID id = addCourseToCartService.handle(new AddCourseToCart(request.getCourseId(), loggedInUser));
        Optional<Cart> cartOpt = cartRepository.findById(id);
        if (cartOpt.isPresent()) {
            return ResponseEntity.ok(toModel(cartOpt.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart resource with " + id + " wasn't found");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = BASE_URL, produces = APPLICATION_JSON_VALUE)
    public CartModel get(@AuthenticationPrincipal CustomUser user) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(user.getId());
        if (cartOpt.isPresent()) {
            return toModel(cartOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart resource with " + user.getId() + " wasn't found");
        }
    }

    private CartModel toModel(Cart cart) {
        List<UUID> courses = Lists.newArrayList(cart.getCourses());
        CartModel model = new CartModel(cart.getId(), cart.getUserId(), courses);
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CartController.class).get(null)).withSelfRel());
        return model;
    }
}
