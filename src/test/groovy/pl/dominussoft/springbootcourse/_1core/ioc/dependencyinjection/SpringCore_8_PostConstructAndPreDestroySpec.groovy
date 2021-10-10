package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringCore_8_PostConstructAndPreDestroySpec extends Specification {


    def "show what bean pre and post processors are for"() {
        expect:
        true
    }


}
