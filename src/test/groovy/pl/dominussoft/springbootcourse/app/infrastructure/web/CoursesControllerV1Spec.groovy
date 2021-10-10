package pl.dominussoft.springbootcourse.app.infrastructure.web

import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.aStudent
import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.anAdmin
import static pl.dominussoft.springbootcourse.app.infrastructure.web.CoursesController.BASE_URL

class CoursesControllerV1Spec extends Specification implements BaseControllerV1Spec {

    def setup() {
        setupRestClient(anAdmin())
    }

    def "create course: successful registration returns http 201, body and location"() {
        given:
        def request = CreateCourseRequestBuilder.aCreateCourseRequest().build()

        when:
        def response = rest.postForEntity(BASE_URL, request, CourseModel)

        then:
        response.getStatusCode() == HttpStatus.CREATED
        def createdCourse = response.getBody()
        def selfUri = new URI(appUrl + BASE_URL + createdCourse.getId())

        createdCourse.id.class == UUID
        response.headers.getLocation() == selfUri
        createdCourse.getRequiredLink(IanaLinkRelations.SELF).toUri() == selfUri
        createdCourse.title == request.title
        createdCourse.getDescription() == request.description
        createdCourse.keywords == request.keywords
        createdCourse.duration == request.duration
    }

    /**
     * https://www.baeldung.com/spring-testexecutionlistener
     * @return
     */
    def "create course: get returns data after successful registration"() {
        given:
        def request = CreateCourseRequestBuilder.aCreateCourseRequest().build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        when:
        def response = rest.getForEntity(BASE_URL + createdCourse.id, CourseModel)

        then:
        response.getStatusCode() == HttpStatus.OK
        def actual = response.body
        actual.getRequiredLink("self").toUri() == new URI(appUrl + BASE_URL + createdCourse.id)
        actual == createdCourse
    }

    def "find all: 2 courses are found: cleaning DB works just fine"() {
        given:
        rest.postForEntity(BASE_URL, CreateCourseRequestBuilder.aCreateCourseRequest().build(), CourseModel)
        rest.postForEntity(BASE_URL, CreateCourseRequestBuilder.aCreateCourseRequest().build(), CourseModel)

        when:
        def response = rest.getForEntity(BASE_URL, CourseModel[])

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 2
    }

    def "get course: non existing course: returns NOT_FOUND"() {
        when:
        def response = rest.getForEntity(BASE_URL + UUID.randomUUID(), CourseModel)

        then:
        response.statusCode == HttpStatus.NOT_FOUND
    }

    def "create course: non admin user: returns FORBIDDEN"() {
        given:
        setupRestClient(aStudent())

        when:
        def response = rest.postForEntity(BASE_URL, CreateCourseRequestBuilder.aCreateCourseRequest().build(), CourseModel)

        then:
        response.statusCode == HttpStatus.FORBIDDEN
    }

    def "create course: wrong credentials: returns UNAUTHORIZED"() {
        given:
        setupRestClient(aStudent().withWrongPassword())

        when:
        def response = rest.postForEntity(BASE_URL, CreateCourseRequestBuilder.aCreateCourseRequest().build(), CourseModel)

        then:
        response.statusCode == HttpStatus.UNAUTHORIZED
    }

    /**
     * Gotcha: this fails without: implementation 'org.apache.httpcomponents:httpclient:4.5.11'
     *
     * See https://jira.spring.io/browse/SPR-9367
     * See https://stackoverflow.com/questions/16748969/java-net-httpretryexception-cannot-retry-due-to-server-authentication-in-strea
     */
    def "create course: no credentials: returns UNAUTHORIZED"() {
        given:
        setupRestClientWithoutCredentials()

        when:
        def response = rest.postForEntity(BASE_URL, CreateCourseRequestBuilder.aCreateCourseRequest().build(), CourseModel)

        then:
        response.statusCode == HttpStatus.UNAUTHORIZED
    }


    // Commented out, not fun working with this client just yet
//    def "create course: successful registration returns http 201, body and location - Use WebTestClient"() {
//        given:
//        Set<String> keywords = ["music", "90"]
//        def request = new CreateCourseRequest(
//                "90's music - crash course", "You will learn all the music from 90's", "2 days",
//                keywords)
//
//        expect:
//        def response = restClient.post().uri(BASE_URL).bodyValue(request)
//                .exchange()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody(Course)
//                .value({ course ->
//                    def selfUri = new URI(appUrl + BASE_URL + course.getId())
//                    course.getRequiredLink(IanaLinkRelations.SELF).toUri() == selfUri
//                    course.getTitle() == request.getTitle()
//                    course.getDescription() == request.getDescription()
//                    course.getKeywords() == request.getKeywords()
//                    course.getDuration() == request.getDuration()
//                })
//                .returnResult()
//        response.getResponseHeaders().getLocation() == new URI(appUrl + BASE_URL + course.getId())
//
//    }
}
