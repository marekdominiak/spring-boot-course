package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.dominussoft.springbootcourse.app.application.configuration.TaxProperties
import spock.lang.Specification

@SpringBootTest
class TaxPropertiesWithProfilesSpec extends Specification {

    @Autowired
    TaxProperties taxProperties

    def "2. tax rates taken from properties from another profile mapped into a Bean "() {
        expect:
        taxProperties.minThreshold == 60
        taxProperties.midThreshold == 70
        taxProperties.maxThreshold == 90
    }

}
