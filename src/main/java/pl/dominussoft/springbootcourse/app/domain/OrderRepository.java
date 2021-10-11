package pl.dominussoft.springbootcourse.app.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {

    Optional<Order> findById(UUID uuid);

    List<Order> findByUserId(UUID userId);
}
