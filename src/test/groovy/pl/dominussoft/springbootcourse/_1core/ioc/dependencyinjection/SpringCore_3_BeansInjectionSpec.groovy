package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_3_BeansInjectionSpec extends Specification {

    @Autowired
    EchoService3 echo3

    def "spring context loads successfully with @Bean configuration"() {
        expect:
        echo3 != null
        echo3.echo("Test") ==  "Echo3: Test"
    }
}
