package pl.dominussoft.springbootcourse.app.infrastructure.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import pl.dominussoft.springbootcourse.app.infrastructure.web.InstructorModel
import pl.dominussoft.springbootcourse.app.infrastructure.web.RegisterInstructorRequest
import spock.lang.Specification

/**
 * Gotchas:
 * https://github.com/spring-projects/spring-hateoas/issues/222
 */
@ActiveProfiles("no-security")
//@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InstructorsControllerV1Spec extends Specification {

    @LocalServerPort
    int localServerPort

    String appUrl

    TestRestTemplate rest = new TestRestTemplate()

    @Autowired
    RestTemplateBuilder restTemplateBuilder

    ObjectMapper obj = new ObjectMapper()

    def setup() {
        this.appUrl = "http://localhost:" + localServerPort
        def builder = restTemplateBuilder.rootUri(appUrl)
        this.rest = new TestRestTemplate(builder, null, null, null)
    }

    // TODO move all fluff to a helper
    // TODO should the getBody use String and we should on our own deserialize?
    def "instructor registration: successful registration returns http 201, body and location"() {
        given:
        Set<String> keywords = ["music", "90"]
        def request = new RegisterInstructorRequest(
                "Rick", "R.", "He will never gonna let you down.", keywords)

        when:
        def response = rest.postForEntity("/instructors", request, InstructorModel)

        then:
        response.getStatusCode() == HttpStatus.CREATED
        def registeredInstructor = response.getBody()
        registeredInstructor.getId().getClass() == UUID
        response.getHeaders().getLocation() == new URI(appUrl + "/instructors/" + registeredInstructor.getId())
        registeredInstructor.getFirstName() == request.getFirstName()
    }

    def "instructor registration: get returns data after successful registration"() {
        given:
        Set<String> keywords = ["music","90"]
        def request = new RegisterInstructorRequest(
                "Rick", "R.", "He will never gonna let you down.", keywords)
        def response = rest.postForEntity("/instructors", request, InstructorModel)
        def instructor = response.getBody()

        when:
        def getResponse = rest.getForEntity("/instructors/" + instructor.getId(), InstructorModel)

        then:
        getResponse.getStatusCode() == HttpStatus.OK
        def actual = getResponse.getBody()
        actual.getRequiredLink("self").toUri() == new URI(appUrl + "/instructors/" + instructor.getId())
        actual == instructor
    }

}
