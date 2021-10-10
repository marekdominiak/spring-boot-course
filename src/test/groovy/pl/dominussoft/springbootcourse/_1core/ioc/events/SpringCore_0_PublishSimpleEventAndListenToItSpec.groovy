package pl.dominussoft.springbootcourse._1core.ioc.events


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_0_PublishSimpleEventAndListenToItSpec extends Specification {

    def "publish a simple event and listen to it"() {
        expect:
        true
    }

}
