package pl.dominussoft.springbootcourse.app.infrastructure.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import pl.dominussoft.springbootcourse.app.domain.Instructor
import pl.dominussoft.springbootcourse.app.domain.InstructorBuilder
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository
import pl.dominussoft.springbootcourse.app.domain.InstructorView
import spock.lang.Specification

import static pl.dominussoft.springbootcourse.app.domain.InstructorBuilder.anInstructor

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InstructorRepositorySpec extends Specification {

    @Autowired
    InstructorRepository repository


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
        def found = null

        then:
        found.contains(saved)
    }

    def "3. finding by first name starting with prefix %Mel: works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = null

        then:
        found.contains(saved)
    }

    def "4. finding by first name ending with prefix 'ania': works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = null

        then:
        found.contains(saved)
    }

    def "5. finding by first name containing string 'ani': works fine"() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = null

        then:
        found.contains(saved)
    }


    def "6. finding by first name NOT like Melania: works fine"() {
        given:
        saved(anInstructor().withFirstName("Melania"))
        Instructor kate = saved(anInstructor().withFirstName("Kate"))

        when:
        def found = null

        then:
        found.contains(kate)
    }

    def "7. finding with age > 18"() {
        given:
        Instructor i1 = saved(anInstructor().withAge(17))
        Instructor i2 = saved(anInstructor().withAge(18))
        Instructor i3 = saved(anInstructor().withAge(19))

        when:
        def found = null

        then:
        found.contains(i3)
    }


    def "8. finding with age >= 18"() {
        given:
        Instructor i1 = saved(anInstructor().withAge(17))
        Instructor i2 = saved(anInstructor().withAge(18))
        Instructor i3 = saved(anInstructor().withAge(19))

        when:
        def found = null

        then:
        found.containsAll([i2, i3])
    }

    def "9. finding with age between 15-18"() {
        given:
        Instructor i1 = saved(anInstructor().withAge(1))
        Instructor i2 = saved(anInstructor().withAge(16))
        Instructor i3 = saved(anInstructor().withAge(100))

        when:
        def found = null

        then:
        found.containsAll([i2])
    }

    def "10. finding by first name ordering by age with ordering asc"() {
        given:
        Instructor i1 = saved(anInstructor().withFirstName("Melania").withAge(99))
        Instructor i2 = saved(anInstructor().withFirstName("Melania").withAge(50))
        Instructor i3 = saved(anInstructor().withFirstName("Melania").withAge(1))

        when:
        def found = null

        then:
        found == [i3, i2, i1] as List
    }

    def "11. finding by first name with LIKE custom @query "() {
        given:
        Instructor saved = saved(anInstructor().withFirstName("Melania"))

        when:
        def found = null

        then:
        found.contains(saved)
    }

    def "12. Projections: finding by first name ordering by age with ordering DESC returns projection of data"() {
        given:
        Instructor i1 = saved(anInstructor().withFirstName("Melania").withAge(99))
        Instructor i2 = saved(anInstructor().withFirstName("Melania").withAge(50))
        Instructor i3 = saved(anInstructor().withFirstName("Melania").withAge(1))

        when:
        def found = null

        then:
        found.size() == 3
        found.get(0).class == InstructorView
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
