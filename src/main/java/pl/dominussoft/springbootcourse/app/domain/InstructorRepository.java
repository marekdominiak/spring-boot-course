package pl.dominussoft.springbootcourse.app.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;


public interface InstructorRepository extends PagingAndSortingRepository<Instructor, UUID> {

    Iterable<Instructor> findAll();

    Page<Instructor> findAll(Pageable pageable);

    Instructor save(Instructor instructor);

    Optional<Instructor> findById(UUID uuid);

}
