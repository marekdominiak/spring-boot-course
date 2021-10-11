package pl.dominussoft.springbootcourse.app.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface InstructorRepository {

    Iterable<Instructor> findAll();

    Page<Instructor> findAll(Pageable pageable);

    Instructor save(Instructor instructor);

    Optional<Instructor> findById(UUID uuid);

}
