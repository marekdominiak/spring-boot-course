package pl.dominussoft.springbootcourse.app.infrastructure.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import pl.dominussoft.springbootcourse.app.domain.*
import spock.lang.Specification

import static pl.dominussoft.springbootcourse.app.domain.InstructorBuilder.anInstructor

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InstructorRepositorySpec extends Specification {

    @Autowired
    InstructorRepository repository

    @Autowired
    CourseRepository courseRepository

    @Autowired
    NamedParameterJdbcOperations jdbc

    CartRepository cartRepository

    def setup() {
        cartRepository = new CartRepositoryJdbcImpl(jdbc)
    }


    def "1. finding by first name: works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = repository.findByFirstName("Melania")

        then:
        found.contains(saved)
    }


    def "2. finding by first name with like %elan%: works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = repository.findByFirstNameLike("%elan%")

        then:
        found.contains(saved)
    }

    def "3. finding by first name starting with prefix %Mel: works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = repository.findByFirstNameStartingWith("Mel")

        then:
        found.contains(saved)
    }

    def "4. finding by first name ending with prefix 'ania': works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = repository.findByFirstNameEndingWith("ania")

        then:
        found.contains(saved)
    }

    def "5. finding by first name containing string 'ani': works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = repository.findByFirstNameContaining("ani")

        then:
        found.contains(saved)
    }


    def "6. finding by first name NOT like Melania: works fine"() {
        given:
        saved(anInstructor().withFirstName("Melania"))
        Instructor kate = saved(anInstructor().withFirstName("Kate"))

        when:
        def found = repository.findByFirstNameNotContaining("Melania")

        then:
        found.contains(kate)
    }

    def "7. finding with age > 18"() {
        given:
        Instructor i1 = saved(anInstructor().withAge(17))
        Instructor i2 = saved(anInstructor().withAge(18))
        Instructor i3 = saved(anInstructor().withAge(19))

        when:
        def found = repository.findByAgeGreaterThan(18)

        then:
        found.contains(i3)
    }


    def "8. finding with age >= 18"() {
        given:
        Instructor i1 = saved(anInstructor().withAge(17))
        Instructor i2 = saved(anInstructor().withAge(18))
        Instructor i3 = saved(anInstructor().withAge(19))

        when:
        def found = repository.findByAgeGreaterThanEqual(18)

        then:
        found.containsAll([i2, i3])
    }

    def "9. finding with age between 15-18"() {
        given:
        Instructor i1 = saved(anInstructor().withAge(1))
        Instructor i2 = saved(anInstructor().withAge(16))
        Instructor i3 = saved(anInstructor().withAge(100))

        when:
        def found = repository.findByAgeBetween(15, 18)

        then:
        found.containsAll([i2])
    }

    def "10. finding by first name ordering by age with ordering asc"() {
        given:
        Instructor i1 = saved(anInstructor().withFirstName("Melania").withAge(99))
        Instructor i2 = saved(anInstructor().withFirstName("Melania").withAge(50))
        Instructor i3 = saved(anInstructor().withFirstName("Melania").withAge(1))

        when:
        def found = repository.findByFirstNameOrderByAge("Melania")

        then:
        found == [i3, i2, i1] as List
    }

    def "11. finding by first name with LIKE custom @query "() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = repository.findByFirstNameLikeCustom("%elan%")

        then:
        found.contains(saved)
    }

    def "12. Projections: finding by first name ordering by age with ordering DESC returns projection of data"() {
        given:
        Instructor i1 = saved(anInstructor().withFirstName("Melania").withAge(99))
        Instructor i2 = saved(anInstructor().withFirstName("Melania").withAge(50))
        Instructor i3 = saved(anInstructor().withFirstName("Melania").withAge(1))

        when:
        def found = repository.findByProjection("Melania")

        then:
        found.size() == 3
        found.get(0).class == InstructorView
        found.get(0).firstName1 == "Melania"
        found.get(0).lastName1 == "R."
        found.get(0).age1 == "99"
    }

    def "13. Projections: with join"() {
        given:
        def course = CourseBuilder.aCourse().build()
        course = courseRepository.save(course)

        def course2 = CourseBuilder.aCourse().build()
        course2 = courseRepository.save(course2)

        def cart = CartBuilder.aCart().build()

        cart.add(course)
        cart.add(course2)
        cart = cartRepository.save(cart)

        when:
        def found = courseRepository.report()

        then:
        found.size() == 2

    }

//
//    def "finding by first name with like: works fine"() {
//        given:
//        Instructor saved = save(anInstructor().withFirstName("Melania"))
//
//        when:
//        def found = repository.findByFirstNameLike("Mel")
//
//        then:
//        found.contains(saved)
//    }

    private Instructor saved(InstructorBuilder instructor) {
        def saved = repository.save(instructor.build())
        saved
    }

}
