package pl.dominussoft.springbootcourse.app.infrastructure.web

import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.PagedModel
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import static RegisterInstructorRequestBuilder.aRegisterInstructorRequest
import static pl.dominussoft.springbootcourse.app.infrastructure.web.InstructorsController.BASE_URL

/**
 * Gotchas:
 * https://github.com/spring-projects/spring-hateoas/issues/222
 */
//@Transactional  <--??
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class InstructorsControllerV2Spec extends Specification implements BaseControllerV0Spec {

    def setup() {
        setupRestClient()
    }

    def "instructor registration: successful registration returns http 201, body and location"() {
        given:
        def request = aRegisterInstructorRequest().build()

        when:
        def response = rest.postForEntity(BASE_URL, request, InstructorModel)

        then:
        response.statusCode == HttpStatus.CREATED
        def registeredInstructor = response.body
        registeredInstructor.id.getClass() == UUID
        response.headers.getLocation() == new URI(appUrl + BASE_URL + "/" + registeredInstructor.id)
        registeredInstructor.firstName == request.firstName
    }


    def "instructor registration: get returns data after successful registration"() {
        given:
        def request = aRegisterInstructorRequest().build()

        def response = rest.postForEntity(BASE_URL, request, InstructorModel)
        def instructor = response.getBody()

        when:
        def getResponse = rest.getForEntity(BASE_URL + "/" + instructor.getId(), InstructorModel)

        then:
        getResponse.getStatusCode() == HttpStatus.OK
        def actual = getResponse.getBody()
        actual.getRequiredLink("self").toUri() == new URI(appUrl + BASE_URL + "/" + instructor.getId())
        actual == instructor
    }

    def "find all: none instructors are found"() {
        when:
        def response = rest.exchange(BASE_URL, HttpMethod.GET, emptyBody(), new ParameterizedTypeReference<PagedModel<InstructorModel>>() {
        })

        then:
        response.body.size() == 0
    }

    def "find with pagination: #size instructors in db, found #expectedPageSize in page #page"() {
        given:
        for (int i in 1..noOfInstructors) {
            def request = aRegisterInstructorRequest().build()
            rest.postForEntity(BASE_URL, request, InstructorModel)
        }

        when:
        def response = getWithPaging(page, size, sort)

        then:
        response.body.size() == expectedPageSize

        where:
        noOfInstructors | page | size | sort        || expectedPageSize
        10              | 0    | 10   | "firstName" || 10
        10              | 0    | 20   | "firstName" || 10
        10              | 0    | 5    | "firstName" || 5
    }

    def "find with sort by first name: alphabetical order returned"() {
        given:
        names.forEach { createdInstructorWithFirstName(it) }

        when:
        def response = getWithPaging(0, 10, sort)

        then:
        response.body.content.collect { it.firstName } == expected

        where:
        names                                     | sort             || expected
        ["Albert", "Hubert", "Zbigniew"]          | "firstName,asc"  || ["Albert", "Hubert", "Zbigniew"]
        ["Albert", "Hubert", "Zbigniew"]          | "firstName,desc" || ["Zbigniew", "Hubert", "Albert"]
        ["Hubert", "Albert", "Zbigniew", "Gosia"] | "firstName,desc" || ["Zbigniew", "Hubert", "Gosia", "Albert"]

    }

    private ResponseEntity<InstructorModel> createdInstructorWithFirstName(String firstName) {
        rest.postForEntity(BASE_URL, aRegisterInstructorRequest().withFirstName(firstName).build(), InstructorModel)
    }

    ResponseEntity<PagedModel<InstructorModel>> getWithPaging(page, size, sort) {
        HttpHeaders headers = new HttpHeaders()
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(appUrl + BASE_URL)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", sort)

        HttpEntity<?> entity = new HttpEntity<>(headers)
        rest.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<PagedModel<InstructorModel>>() {
        })
    }

}
