//package pl.dominussoft.springbootcourse.app.infrastructure.persistence;
//
//import org.springframework.data.repository.CrudRepository;
//import pl.dominussoft.springbootcourse.app.domain.Order;
//import pl.dominussoft.springbootcourse.app.domain.OrderRepository;
//
//import java.util.Optional;
//import java.util.UUID;
//
//public interface OrderRepositorySpringData extends CrudRepository<Order, UUID>, OrderRepository {
//    <S extends Order> S save(S entity);
//    Iterable<Order> findAll();
//    Optional<Order> findById(UUID uuid);
//}
