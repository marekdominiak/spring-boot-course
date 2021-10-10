package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import pl.dominussoft.springbootcourse.app.domain.CustomInstructorRepository;
import pl.dominussoft.springbootcourse.app.domain.Instructor;

import java.util.Map;
import java.util.UUID;

@Component
public class CustomInstructorRepositoryImpl implements CustomInstructorRepository {

    private static final String INSERT_INSTRUCTOR = "INSERT INTO instructor(id, first_name, last_name, bio) " +
            "VALUES (:id, :first_name, :last_name, :bio)";

    final NamedParameterJdbcOperations jdbc;

    public CustomInstructorRepositoryImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    //    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.READ_COMMITTED)
    @Override
    public <S extends Instructor> S save(S instructor) {
        // not correctly handles updates (just a showcase of a CustomRepository methods
        // and Having not transactions on that level
        instructor.setId(UUID.randomUUID());
        jdbc.update(INSERT_INSTRUCTOR, Map.of(
                "id", instructor.getId(),
                "first_name", instructor.getFirstName(),
                "last_name", instructor.getLastName(),
                "bio", instructor.getBio()
                )
        );
        return instructor;
    }

}
