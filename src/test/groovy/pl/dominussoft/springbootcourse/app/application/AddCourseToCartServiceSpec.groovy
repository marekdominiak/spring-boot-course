package pl.dominussoft.springbootcourse.app.application


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import pl.dominussoft.springbootcourse.app.application.configuration.ServiceTest
import pl.dominussoft.springbootcourse.app.domain.CourseAddedToCartEvent
import pl.dominussoft.springbootcourse.app.domain.CourseRepository
import pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static pl.dominussoft.springbootcourse.app.domain.CourseBuilder.aCourse

/**
 * https://spockframework.org/spock/docs/2.0/module_spring.html
 */
@ServiceTest
class AddCourseToCartServiceSpec extends Specification {

    @Autowired
    AddCourseToCartService service

    @Autowired
    CourseRepository courseRepository

    @Autowired
    UserAccountRepository userAccountRepository

    @Autowired
    ApplicationEventPublisher applicationEventPublisher

    def "1. course added to cart: event CourseAddedToCartEvent is published"() {
        given:
        def course = courseRepository.save(aCourse().build())
        def student = userAccountRepository.save(UserAccountBuilder.aStudent().build())

        when:
        service.handle(new AddCourseToCart(course.id, student.id))

        then:
        1 * applicationEventPublisher.publishEvent(_) >> { CourseAddedToCartEvent event ->
            assert event.user == student
            assert event.course == course
        }
    }

    @TestConfiguration
    static class MockConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        @Primary
        ApplicationEventPublisher applicationEventPublisher() {
            return detachedMockFactory.Mock(ApplicationEventPublisher)
        }
    }
}
