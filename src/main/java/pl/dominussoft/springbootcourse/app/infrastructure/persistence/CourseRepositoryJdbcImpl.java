package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.Currency;
import pl.dominussoft.springbootcourse.app.domain.Price;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Shown to compare implementations.
 */
@RequiredArgsConstructor
public class CourseRepositoryJdbcImpl implements CourseRepository {

    public static final String INSERT_COURSE = "insert into course (id, title, description, duration, amount, currency, keywords) values (:id, :title, :description, :duration, :amount, :currency, :keywords)";
    public static final String UPDATE_COURSE = "update course set title = :title, description = :description, duration = :duration, amount = :amount, currency =:currency, keywords = :keywords where id = :id";
    public static final String FIND_ONE_BY_ID = "select c.* from course c where c.id = :id";
    public static final String FIND_ALL = "select c.* from course c order by c.id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DURATION = "duration";
    public static final String AMOUNT = "amount";
    public static final String CURRENCY = "currency";
    public static final String KEYWORDS = "keywords";

    private final NamedParameterJdbcOperations jdbc;

    public Optional<Course> findById(UUID id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id.toString());
        try {
            return Optional.ofNullable(jdbc.queryForObject(FIND_ONE_BY_ID, params, this::mapRow));
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new ResourceNotFoundException(String.format("Course with id: %s doesn't exist", id));
            // alternative version for Spring 5.0
            // throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Course with id: %s doesn't exist", id));
        }
    }

    @Override
    public Course save(Course course) {
        if (course.getId() != null) {
            jdbc.update(UPDATE_COURSE, Map.of(
                    TITLE, course.getTitle(),
                    DESCRIPTION, course.getDescription(),
                    DURATION, course.getDuration(),
                    AMOUNT, course.getPrice().getAmount(),
                    CURRENCY, course.getPrice().getCurrency().toString(),
                    KEYWORDS, course.getKeywords().stream().toArray(String[]::new)
                    )
            );
        } else {
            course.setId(UUID.randomUUID());
            jdbc.update(INSERT_COURSE, Map.of(
                    "id", course.getId(),
                    TITLE, course.getTitle(),
                    DESCRIPTION, course.getDescription(),
                    DURATION, course.getDuration(),
                    AMOUNT, course.getPrice().getAmount(),
                    CURRENCY, course.getPrice().getCurrency().toString(),
                    KEYWORDS, course.getKeywords().stream().toArray(String[]::new)
                    )
            );
        }
        return course;
    }

    public List<Course> findAll() {
        return jdbc.query(FIND_ALL, this::mapRow);
    }


    private Set<String> keywordsFrom(ResultSet rs) throws SQLException {
        Array keywords = rs.getArray(KEYWORDS);
        Object[] keywordsO = (Object[]) keywords.getArray();
        return Stream.of(keywordsO)
                .map(Object::toString)
                .collect(Collectors.toSet());
    }


    private Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course(
                rs.getString(TITLE),
                rs.getString(DESCRIPTION),
                rs.getString(DURATION),
                keywordsFrom(rs),
                new Price(rs.getBigDecimal(AMOUNT), Currency.valueOf(rs.getString(CURRENCY)))
        );
        course.setId(UUID.fromString(rs.getString("id")));
        return course;
    }
}
