package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.dominussoft.springbootcourse.app.application.configuration.TaxProperties
import spock.lang.Specification

@SpringBootTest
class TaxPropertiesSpec extends Specification {

    @Autowired
    TaxProperties taxProperties

    def "1. tax rates taken from properties mapped into a Bean "() {
        expect:
        taxProperties.minThreshold == 10
        taxProperties.midThreshold == 50
        taxProperties.maxThreshold == 90
    }

}
