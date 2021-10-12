package pl.dominussoft.springbootcourse.app.domain;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends CrudRepository<Course, UUID> {

    Optional<Course> findById(UUID courseId);

    Course save(Course course);

    List<Course> findAll();

    @Query("select c.title as title, c.id as course_id, cc.cart_id as cart_id  from course c join cart_course cc on cc.course_id = c.id")
    List<CartCourseView> report();
}
