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
     * Use application.tax percent from test/resources/application.properties
     */
    def "1. tax rate calculated based on injected @Value"() {
        given:
        def order = OrderBuilder.anOrder().build()

        when:
        def percent = taxCalculator.calculateTaxPercent(order)

        then:
        percent == 40
    }

    def "2. tax rate as string (application.tax.percent.string) based on another property (application.tax.percent) with SpEL"() {
        when:
        def taxRateAsString = taxCalculator.presentTaxRate()

        then:
        taxRateAsString == "Tax percent 40"
    }

}
