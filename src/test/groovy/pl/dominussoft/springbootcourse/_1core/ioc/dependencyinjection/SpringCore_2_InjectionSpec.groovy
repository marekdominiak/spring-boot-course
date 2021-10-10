package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_2_InjectionSpec extends Specification {

    @Autowired
    EchoService2 echo2

    def "spring context loads successfully with dependency in test"() {
        expect:
        echo2 != null
        echo2.echo("Test") ==  "Echo2: Test"
    }
}
