package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import pl.dominussoft.springbootcourse.app.domain.CourseBuilder
import spock.lang.Specification

@Transactional
@SpringBootTest
class CourseFinderSpec extends Specification {

    @Autowired
    CourseFinder finder

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
        // save data through repository

        when:
        def courses = finder.findAll()

        then:
        courses.size() == 2
    }

}
