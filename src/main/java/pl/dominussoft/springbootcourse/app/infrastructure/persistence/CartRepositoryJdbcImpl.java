package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.dominussoft.springbootcourse.app.domain.Cart;
import pl.dominussoft.springbootcourse.app.domain.CartRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CartRepositoryJdbcImpl implements CartRepository {

    private static final String FIND_ONE_BY_USER_ID =
            "select c.id, c.user_id, cc.course_id  from cart c left join cart_course cc on c.id = cc.cart_id where c.user_id = :id";
    private static final String FIND_ONE_BY_ID =
            "select c.id, c.user_id, cc.course_id  from cart c left join cart_course cc on c.id = cc.cart_id where c.id = :id";

    private static final String INSERT_CART_INFORMATION = "INSERT INTO cart(id, user_id) VALUES (:id, :user_id)";
    private static final String DELETE_ALL_COURSES_FROM_CART = "delete from cart_course where cart_id = :id";
    private static final String INSERT_COURSE_TO_CART_INFORMATION = "insert into cart_course(cart_id, course_id) values (:cart_id, :course_id)";


    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Cart> findById(UUID id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id.toString());
        try {
            return Optional.ofNullable(convert(jdbc.queryForList(FIND_ONE_BY_ID, params)));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Cart> findByUserId(UUID id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id.toString());
        try {
            return Optional.ofNullable(convert(jdbc.queryForList(FIND_ONE_BY_USER_ID, params)));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    private Cart convert(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            return null;
        }

        Map<String, Object> firstResult = results.get(0);
        String userId = firstResult.get("user_id").toString();
        String id = firstResult.get("id").toString();

        Set<String> courses = new HashSet<>();
        for (Map<String, Object> result : results) {
            courses.add(result.get("course_id").toString());
        }
        Set<UUID> coursesIds = courses.stream().map(UUID::fromString).collect(Collectors.toSet());
        Cart cart = new Cart(UUID.fromString(userId), coursesIds);
        cart.setId(UUID.fromString(id));
        return cart;
    }

    @Override
    public Cart save(Cart cart) {
        if (cart.getId() != null) {
            jdbc.update(DELETE_ALL_COURSES_FROM_CART, Map.of("id", cart.getId()));
        } else {
            cart.setId(UUID.randomUUID());
            jdbc.update(INSERT_CART_INFORMATION, Map.of("id", cart.getId(), "user_id", cart.getUserId()));
        }
        insertCoursesToCart(cart);
        return findById(cart.getId()).get();
    }

    private void insertCoursesToCart(Cart cart) {
        for (UUID courseId : cart.getCourses()) {
            jdbc.update(INSERT_COURSE_TO_CART_INFORMATION, Map.of("cart_id", cart.getId(), "course_id", courseId));
        }
    }
}
