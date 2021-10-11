package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.dominussoft.springbootcourse.app.domain.OrderBuilder
import spock.lang.Specification

@SpringBootTest
class TaxCalculatorSpec extends Specification {

    @Autowired
    TaxCalculator taxCalculator

    /**
     * Use application.tax.percent from application properties
     */
    def "tax rate calculated based on injected @Value"() {
        given:
        def order = OrderBuilder.anOrder().build()

        when:
        def percent = taxCalculator.calculateTaxPercent(order)

        then:
        percent == 40
    }

}
