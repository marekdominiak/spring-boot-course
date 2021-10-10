package pl.dominussoft.springbootcourse.app.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
    <S extends Order> S save(S entity);

    Iterable<Order> findAll();

    Optional<Order> findById(UUID uuid);
}
