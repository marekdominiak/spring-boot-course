package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import pl.dominussoft.springbootcourse.app.domain.OrderBuilder
import pl.dominussoft.springbootcourse.app.domain.OrderRepository
import spock.lang.Specification

@Transactional
@SpringBootTest
class OrderFinderSpec extends Specification {

    @Autowired
    OrderFinder finder

    @Autowired
    OrderRepository repository

    def "find all: returns empty results"() {
        when:
        def order = finder.findOrder(UUID.randomUUID())

        then:
        order == null
    }

    def "find all: returns some orders"() {
        given:
        def order1 = OrderBuilder.anOrder().build()
        def order2 = OrderBuilder.anOrder().build()
        repository.save(order1)
        repository.save(order2)

        when:
        def orders = finder.findAll()

        then:
        orders.size() == 2
    }

}
