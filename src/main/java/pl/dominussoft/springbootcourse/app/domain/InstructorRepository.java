package pl.dominussoft.springbootcourse.app.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface InstructorRepository extends CrudRepository<Instructor, UUID> {

    Iterable<Instructor> findAll();

    Page<Instructor> findAll(Pageable pageable);

    Instructor save(Instructor instructor);

    Optional<Instructor> findById(UUID uuid);

    List<Instructor> findByFirstName(String firstName);

    List<Instructor> findByFirstNameLike(String like);

    List<Instructor> findByFirstNameStartingWith(String startingWith);

    List<Instructor> findByFirstNameEndingWith(String endingWith);

    List<Instructor> findByFirstNameContaining(String containing);

    List<Instructor> findByFirstNameNotContaining(String notContaining);

    List<Instructor> findByAgeGreaterThan(Integer greaterThan);

    List<Instructor> findByAgeGreaterThanEqual(Integer greaterThanEqual);

    List<Instructor> findByAgeBetween(Integer from, Integer to);

    List<Instructor> findByFirstNameOrderByAge(String firstName);

    @Query("SELECT * FROM INSTRUCTOR WHERE first_name like :likeString")
    List<Instructor> findByFirstNameLikeCustom(String likeString);

    @Query("SELECT first_name, last_name, age FROM INSTRUCTOR WHERE first_name = :firstName order by age DESC")
    List<InstructorView> findByFirstNameOrderByAgeDescCustom(String firstName);

    @Query("select Age as age1, FIRST_NAME as first_name1, LAST_NAME last_name1 from INSTRUCTOR u where u.FIRST_NAME LIKE :firstName ORDER BY Age DESC")
    List<InstructorView> findByProjection(@Param("firstName") String firstName);


    // Possible in JPA: / Spring Data JPA -> https://www.baeldung.com/jpa-queries-custom-result-with-aggregation-functions
    // @Query("select new InstructorView(u.age, u.first_name, u.first_name) from INSTRUCTOR u where u.FIRST_NAME LIKE :firstName ORDER BY Age DESC")
    //List<InstructorView> findByProjection2(@Param("firstName") String firstName);

}
