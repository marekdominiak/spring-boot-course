package pl.dominussoft.springbootcourse.app.infrastructure.web

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.hateoas.IanaLinkRelations
import pl.dominussoft.springbootcourse.app.domain.Role
import pl.dominussoft.springbootcourse.app.domain.UserAccount
import spock.lang.Specification

class CartControllerV0Spec extends Specification implements BaseControllerV1Spec {

    UUID course1
    UUID course2

    def setup() {
        def user = new UserAccount("Admin", "Admin", "Admin@ecourses.pl", encoder.encode("admin"), Role.ADMIN)
        def rawPassword = user.getPassword()
        this.loggedInUser = accountRepository.save(loggedInUser)
        this.appUrl = "http://localhost:" + localServerPort
        def builder = restTemplateBuilder.rootUri(appUrl)
        this.rest = new TestRestTemplate(builder, user.getEmail(), rawPassword, null)
        def c1 = new CreateCourseRequest("title", "desctiopt", "duration", Set.of("soem", "many", "keywords"),
                new Price(BigDecimal.TEN, "USD"))
        def response1 = rest.postForEntity(CoursesController.BASE_URL, c1, CourseModel)
        this.course1 = response1.body.id
        def c2 = new CreateCourseRequest("title", "desctiopt", "duration", Set.of("soem", "many", "keywords"),
                new Price(BigDecimal.TEN, "USD"))
        def response = rest.postForEntity(CoursesController.BASE_URL, c2, CourseModel)
        course2 = response.body.id
    }

    def "add"() {
        when:
        def user = new UserAccount("Student", "Student", "student@ecourses.pl", encoder.encode("student"), Role.STUDENT)
        def rawPassword = user.getPassword()
        this.loggedInUser = accountRepository.save(loggedInUser)
        this.appUrl = "http://localhost:" + localServerPort
        def builder = restTemplateBuilder.rootUri(appUrl)
        this.rest = new TestRestTemplate(builder, user.getEmail(), rawPassword, null)
        def response = rest.postForEntity("/cart/", new AddCourseToCartRequest(course1), CartModel)

        then:
        response.statusCode.value() == 200
        def cart = response.body
        def selfUri = new URI(appUrl + "/cart/")
        cart.getRequiredLink(IanaLinkRelations.SELF).toUri() == selfUri
        cart.courses as Set == [course1] as Set
        cart.userId == loggedInUser.id
    }


    def "courses in cart"() {
        when:
        def user = new UserAccount("Student", "Student", "student@ecourses.pl", encoder.encode("student"), Role.STUDENT)
        def rawPassword = user.getPassword()
        this.loggedInUser = accountRepository.save(loggedInUser)
        this.appUrl = "http://localhost:" + localServerPort
        def builder = restTemplateBuilder.rootUri(appUrl)
        this.rest = new TestRestTemplate(builder, user.getEmail(), rawPassword, null)
        rest.postForEntity("/cart/", new AddCourseToCartRequest(course1), CartModel)
        rest.postForEntity("/cart/", new AddCourseToCartRequest(course2), CartModel)

        def response = rest.getForEntity("/cart/", CartModel)

        then:
        response.statusCode.value() == 200
        def body = response.body
        def selfUri = new URI(appUrl + "/cart/")
        body.getRequiredLink(IanaLinkRelations.SELF).toUri() == selfUri
        body.courses as Set == [course1, course2] as Set
        body.userId == loggedInUser.id
    }

}
