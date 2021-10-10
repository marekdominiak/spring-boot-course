package pl.dominussoft.springbootcourse.app.domain;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Optional<Order> findById(UUID uuid);
}
