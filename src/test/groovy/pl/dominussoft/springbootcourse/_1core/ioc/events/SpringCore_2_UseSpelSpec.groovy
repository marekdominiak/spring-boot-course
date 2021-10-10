package pl.dominussoft.springbootcourse._1core.ioc.events


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_2_UseSpelSpec extends Specification {

    def "Use condition and SpEL to skip some listeners"() {
        expect:
        true
    }

}
