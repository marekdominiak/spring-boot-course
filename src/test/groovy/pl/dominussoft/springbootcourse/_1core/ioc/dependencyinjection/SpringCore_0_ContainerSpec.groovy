package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@RequiredArgsConstructor
@SpringBootTest
class SpringCore_0_ContainerSpec extends Specification {

    @Autowired
    private BeanFactory beanFactory


    def "spring context loads"() {
        expect:
        beanFactory != null
    }

    def "show default properties of components in IoC container"() {
        when:
        EchoService0 byClass = beanFactory.getBean(EchoService0.class)
        EchoService0 byName = (EchoService0) beanFactory.getBean("echoService0");

        then:
        byClass == byName
        beanFactory.isSingleton("echoService0")
        beanFactory.getAliases("echoService0") == ([] as String [])
    }

}
