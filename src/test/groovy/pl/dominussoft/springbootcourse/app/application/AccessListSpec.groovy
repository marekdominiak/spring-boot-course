package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AccessListSpec extends Specification {

    @Autowired
    AllowedDomains allowedDomains

    /**
     * Use application.tax.percent from application properties
     */
    def "tax rate calculated based on injected @Value"() {
        when:
        def list = allowedDomains.getAccessList()

        then:
        list == ['@example.com', '@example1.com', '@example2.com']
    }

}
