package pl.dominussoft.springbootcourse._1core.ioc.events


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_1_SecondListenerSpec extends Specification {

    def "second listener for free"() {
        expect:
        true
    }

}
