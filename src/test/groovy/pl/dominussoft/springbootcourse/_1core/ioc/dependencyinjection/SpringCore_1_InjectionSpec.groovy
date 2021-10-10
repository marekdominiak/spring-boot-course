package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection

import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_1_InjectionSpec extends Specification {

    @Autowired
    BeanFactory beanFactory

    def "spring context loads successfully with use of component annotation"() {
        when:
        EchoService1 echo = beanFactory.getBean(EchoService1.class)

        then:
        echo != null
        echo.echo("Test") ==  "Echo1: Test"
    }
}
