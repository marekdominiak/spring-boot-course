package pl.dominussoft.springbootcourse.app.domain;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    Optional<Cart> findById(UUID id);

    Optional<Cart> findByUserId(UUID id);

    default Optional<Cart> findByUserId(UserAccount userAccount) {
        return findByUserId(userAccount.getId());
    }

    Cart save(Cart cart);
}
