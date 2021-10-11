package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest
class CoursesCacheServiceSpec extends Specification {

    @Autowired
    CoursesCacheService service

    def "show running @PreDestroy and @PostConstruct "() {
        expect:
        service.doSomething()
        service.doSomething()
        service.doSomething()
        service.doSomething()

    }

}
