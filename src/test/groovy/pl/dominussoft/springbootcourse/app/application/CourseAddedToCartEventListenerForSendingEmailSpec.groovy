package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import pl.dominussoft.springbootcourse.app.application.configuration.ServiceTest
import pl.dominussoft.springbootcourse.app.domain.CourseRepository
import pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static pl.dominussoft.springbootcourse.app.domain.CourseBuilder.aCourse

// to make DetachedMockFactory work for us. Possibly we can make this class Dirty
@SpringBootTest(properties = ['spring.main.allow-bean-definition-overriding=true'])
@ServiceTest
class CourseAddedToCartEventListenerForSendingEmailSpec extends Specification {

    @Autowired
    AddCourseToCartService service

    @Autowired
    CourseRepository courseRepository

    @Autowired
    UserAccountRepository userAccountRepository

    @Autowired
    ApplicationEventPublisher applicationEventPublisher

    @Autowired
    MailSender mailSender

    @Autowired
    SmsSender smsSender

    def "1. course added to cart: email and sms are send to an admin with two listeners"() {
        given:
        def course = courseRepository.save(aCourse().build())
        def student = userAccountRepository.save(UserAccountBuilder.aStudent().build())

        when:
        service.handle(new AddCourseToCart(course.id, student.id))

        then:
        1 * mailSender.send(_)
        1 * smsSender.send(_)
    }

    @TestConfiguration
    static class MockConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        @Primary
        MailSender mailSender() {
            return detachedMockFactory.Mock(MailSender)
        }

        @Bean
        @Primary
        SmsSender smsSender() {
            return detachedMockFactory.Mock(SmsSender)
        }
    }
}
