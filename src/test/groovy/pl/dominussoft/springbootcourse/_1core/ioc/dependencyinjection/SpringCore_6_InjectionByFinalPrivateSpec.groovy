package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_6_InjectionByFinalPrivateSpec extends Specification {

    @Autowired
    Service4 service4

    def "not @Autowired/@Inject annotation but injection works"() {
        expect:
        service4.wrapper("!") == "wrapper: (Echo: !)"
    }


}
