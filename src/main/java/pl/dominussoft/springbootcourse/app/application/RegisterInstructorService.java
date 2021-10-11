package pl.dominussoft.springbootcourse.app.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dominussoft.springbootcourse.app.domain.Instructor;
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository;

import java.util.UUID;

@Service
public class RegisterInstructorService {

    private final InstructorRepository repository;

    public RegisterInstructorService(InstructorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UUID registerInstructor(RegisterInstructor cmd) {
        final var instructor = newInstructor(cmd);
        final var saved = repository.save(instructor);
        return saved.getId();
    }

    private Instructor newInstructor(RegisterInstructor cmd) {
        return new Instructor(cmd.getFirstName(), cmd.getLastName(), cmd.getBio(), 42, cmd.getKeywords());
    }

    public UUID registerInstructorNoTx(RegisterInstructor cmd) {
        final var instructor = newInstructor(cmd);
        final var saved = repository.save(instructor);
        return saved.getId();
    }

    public UUID registerInstructorNoTxThrow(RegisterInstructor cmd) {
        final var instructor = newInstructor(cmd);
        final var saved = repository.save(instructor);
        throw new RuntimeException("Boom");
    }

}
