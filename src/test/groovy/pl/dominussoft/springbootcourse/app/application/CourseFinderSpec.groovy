package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import pl.dominussoft.springbootcourse.app.domain.CourseBuilder
import pl.dominussoft.springbootcourse.app.domain.CourseRepository
import spock.lang.Specification

@Transactional
@SpringBootTest
class CourseFinderSpec extends Specification {

    @Autowired
    CourseFinder finder

    @Autowired
    CourseRepository courseRepository

    def "find all: returns empty results"() {
        when:
        def course = finder.findCourseBy(UUID.randomUUID())

        then:
        course == null
    }

    def "find all: returns some courses"() {
        given:
        def course1 = CourseBuilder.aCourse().build()
        def course2 = CourseBuilder.aCourse().build()
        courseRepository.save(course1)
        courseRepository.save(course2)

        when:
        def courses = finder.findAll()

        then:
        courses.size() == 2
    }

}
