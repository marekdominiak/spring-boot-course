package pl.dominussoft.springbootcourse.app.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dominussoft.springbootcourse.app.domain.Instructor;
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterInstructorService {

    private final InstructorRepository repository;

    @Transactional
    public UUID registerInstructor(RegisterInstructor cmd) {
        final var instructor = new Instructor(cmd.getFirstName(), cmd.getLastName(), cmd.getBio(), cmd.getKeywords());
        final var saved = repository.save(instructor);
        return saved.getId();
    }

    public UUID registerInstructorNoTx(RegisterInstructor cmd) {
        final var instructor = new Instructor(cmd.getFirstName(), cmd.getLastName(), cmd.getBio(), cmd.getKeywords());
        final var saved = repository.save(instructor);
        return saved.getId();
    }

    public UUID registerInstructorNoTxThrow(RegisterInstructor cmd) {
        final var instructor = new Instructor(cmd.getFirstName(), cmd.getLastName(), cmd.getBio(), cmd.getKeywords());
        final var saved = repository.save(instructor);
        throw new RuntimeException("Boom");
    }

}
