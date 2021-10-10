package pl.dominussoft.springbootcourse.app.domain;

/**
 * Example of custom methods added to Spring data repository.
 */
public interface CustomInstructorRepository {
    <S extends Instructor> S save(S instructor);
}
