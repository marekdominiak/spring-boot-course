package pl.dominussoft.springbootcourse.app.infrastructure.web


import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static java.util.Collections.emptySet
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK
import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.aStudent
import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.anAdmin
import static pl.dominussoft.springbootcourse.app.infrastructure.web.CoursesController.BASE_URL
import static pl.dominussoft.springbootcourse.app.infrastructure.web.CreateCourseRequestBuilder.aCreateCourseRequest

class CoursesControllerSpec extends Specification implements BaseControllerV1Spec {

    def setup() {
        setupRestClient(anAdmin())
    }

    /**
     * Use /courses/postWithRequestMapping for mapping.
     * Use @RequestMapping annotation.
     */
    def "1. create course: successful registration returns http 200"() {
        given:
        def request = aCreateCourseRequest().build()

        when:
        def response = rest.postForEntity(BASE_URL + "postWithRequestMapping", request, CourseModel)

        then:
        response.getStatusCode() == OK
        response.getBody() == null
    }

    /**
     * Use /courses/postWithPostMapping for mapping.
     * Use @PostMapping annotation
     */
    def "2. create course: successful registration returns http 200"() {
        given:
        def request = aCreateCourseRequest().build()

        when:
        def response = rest.postForEntity(BASE_URL + "postWithPostMapping", request, CourseModel)

        then:
        response.getStatusCode() == OK
        response.getBody() == null
    }

    /**
     * Use /courses/postWithCustomResponseStatus for mapping.
     * Use @PostMapping annotation
     * Use @ResponseStatus to return http status.
     */
    def "3. create course: successful registration returns http 201"() {
        given:
        def request = aCreateCourseRequest().build()

        when:
        def response = rest.postForEntity(BASE_URL + "postWithCustomResponseStatus", request, CourseModel)

        then:
        response.getStatusCode() == HttpStatus.CREATED
        response.getBody() == null
    }

    /**
     * Use /courses/getWithRequestMapping/{id} for mapping.
     * Use @RequestMapping annotation
     * Use @PathVariable annotation
     */
    def "4. get course: returns correct body"() {
        def request = aCreateCourseRequest().build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        when:
        def response = rest.getForEntity(BASE_URL + "getWithRequestMapping/" + createdCourse.id, CourseModel)

        then:
        response.getStatusCode() == OK
        def actual = response.body
        actual.getRequiredLink("self").toUri() == new URI(appUrl + BASE_URL + createdCourse.id)
        actual == createdCourse
    }

    /**
     * Use /courses/getWithGetMapping/{id} for mapping.
     * Use @GetMapping annotation.
     * Use @PathVariable annotation
     */
    def "5. get course: returns correct body"() {
        def request = aCreateCourseRequest().build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        when:
        def response = rest.getForEntity(BASE_URL + "getWithGetMapping/" + createdCourse.id, CourseModel)

        then:
        response.getStatusCode() == OK
        def actual = response.body
        actual.getRequiredLink("self").toUri() == new URI(appUrl + BASE_URL + createdCourse.id)
        actual == createdCourse
    }


    /**
     * Use /courses/putWithPutMapping/{id} for mapping.
     * Use @PutMapping annotation
     */
    def "6. update course: returns correct body"() {
        def request = aCreateCourseRequest().withTitle("Spring Boot Intro").build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        def updateRequest = aCreateCourseRequest().withTitle("Spring Cloud Micro").build()

        when:
        def response = rest.exchange(BASE_URL + "putWithPutMapping/" + createdCourse.id,
                HttpMethod.PUT, new HttpEntity<CreateCourseRequest>(updateRequest, defaultHeaders()),
                CourseModel)

        then:
        response.getStatusCode() == OK
        def actual = response.body
        actual.getRequiredLink("self").toUri() == new URI(appUrl + BASE_URL + createdCourse.id)
        actual.title == "Spring Cloud Micro"
    }


    /**
     * Use /courses/{id} for mapping.
     * Use @DeleteMapping or an alternative version annotation
     */
    def "7. delete course: succeeds"() {
        def request = aCreateCourseRequest().withTitle("Spring Boot Intro").build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        when:
        rest.delete(BASE_URL + createdCourse.id)

        then:
        rest.getForEntity(BASE_URL + createdCourse.id, CourseModel).statusCode == HttpStatus.NOT_FOUND
    }


    /**
     * Use /courses/getWithRequestParam?courseId={id} for mapping.
     * Use @DeleteMapping or an alternative version annotation
     */
    def "8. get course by request param"() {
        def request = aCreateCourseRequest().build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        when:
        def response = rest.getForEntity(BASE_URL + "getWithRequestParam?courseId=" + createdCourse.id, CourseModel)

        then:
        response.getStatusCode() == OK
        def actual = response.body
        actual.getRequiredLink("self").toUri() == new URI(appUrl + BASE_URL + createdCourse.id)
        actual == createdCourse
    }

    /**
     * Use /courses/putWithHeaders/{id} for mapping.
     * Use @PutMapping annotation
     */
    def "9. put course: request headers should be returned in response"() {
        def request = aCreateCourseRequest().withTitle("Spring Boot Intro").build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        def updateRequest = aCreateCourseRequest().withTitle("Spring Cloud Micro").build()

        def headers = defaultHeaders()
        headers.add("ourOwnHeader", "Rick R.")

        when:
        def response = rest.exchange(BASE_URL + "putWithHeaders/" + createdCourse.id,
                HttpMethod.PUT, new HttpEntity<CreateCourseRequest>(updateRequest, headers),
                CourseModel)

        then:
        response.headers.get("ourOwnHeader").get(0) == "Rick R."
    }


    /**
     * Use /courses/postWithPostMapping for mapping.
     * Use @PostMapping annotation
     */
    def "10. create course: validation for #request returns #expectedStatus"() {
        when:
        def response = rest.postForEntity(BASE_URL + "postWithValidation", request, CourseModel)

        then:
        response.statusCode == expectedStatus

        where:
        request                                                                                 || expectedStatus
        new CreateCourseRequest("Spring Boot", "Description", "duration", emptySet(), pln(100)) || OK
        new CreateCourseRequest("Spring Boot", "Description", null, emptySet(), pln(100))       || OK
        new CreateCourseRequest(null, "Description", "duration", emptySet(), pln(100))          || BAD_REQUEST
        new CreateCourseRequest("Spring Boot", null, "duration", emptySet(), pln(100))          || BAD_REQUEST
        new CreateCourseRequest("Spring Boot", "Description", "duration", emptySet(), null)     || BAD_REQUEST
    }


    // @RequestParam
    // zmiana headerow w requescie i response
    // validacja

    // data repository tests


    def "X. create course: successful registration returns http 201, body and location"() {
        given:
        def request = aCreateCourseRequest().build()

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
        def request = aCreateCourseRequest().build()
        def createdCourse = rest.postForEntity(BASE_URL, request, CourseModel).body

        when:
        def response = rest.getForEntity(BASE_URL + createdCourse.id, CourseModel)

        then:
        response.getStatusCode() == OK
        def actual = response.body
        actual.getRequiredLink("self").toUri() == new URI(appUrl + BASE_URL + createdCourse.id)
        actual == createdCourse
    }

    def "find all: 2 courses are found: cleaning DB works just fine"() {
        given:
        rest.postForEntity(BASE_URL, aCreateCourseRequest().build(), CourseModel)
        rest.postForEntity(BASE_URL, aCreateCourseRequest().build(), CourseModel)

        when:
        def response = rest.getForEntity(BASE_URL, CourseModel[])

        then:
        response.statusCode == OK
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
        def response = rest.postForEntity(BASE_URL, aCreateCourseRequest().build(), CourseModel)

        then:
        response.statusCode == HttpStatus.FORBIDDEN
    }

    def "create course: wrong credentials: returns UNAUTHORIZED"() {
        given:
        setupRestClient(aStudent().withWrongPassword())

        when:
        def response = createCourse()

        then:
        response.statusCode == HttpStatus.UNAUTHORIZED
    }

    private ResponseEntity<CourseModel> createCourse() {
        rest.postForEntity(BASE_URL, aCreateCourseRequest().build(), CourseModel)
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
        def response = rest.postForEntity(BASE_URL, aCreateCourseRequest().build(), CourseModel)

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


    private static Price pln(Integer amount) {
        new Price(BigDecimal.valueOf(amount), "PLN")
    }
}