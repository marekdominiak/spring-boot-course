package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest
class OrderFinderSpec extends Specification {

    @Autowired
    OrderFinder finder

    def "find all: returns empty results"() {
        given:

        when:
        def orders = finder.findOrder(UUID.randomUUID())

        then:
        orders.isEmpty()
    }

}
