package pl.dominussoft.springbootcourse.app.infrastructure.web

import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.aStudent
import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.anAdmin
import static pl.dominussoft.springbootcourse.app.infrastructure.web.CartController.BASE_URL

class CartControllerSpec extends Specification implements BaseControllerV1Spec {

    UUID course1
    UUID course2

    def setup() {
        setupRestClient(anAdmin())
        course1 = anyCourse()
        course2 = anyCourse()
    }

    def "add a course to a cart: successful addition returns http 200, body and location"() {
        given:
        setupRestClient(aStudent())

        when:
        def response = rest.postForEntity(BASE_URL, new AddCourseToCartRequest(course1), CartModel)

        then:
        response.statusCode == HttpStatus.OK
        def cart = response.body

        def selfUri = new URI(appUrl + BASE_URL)

        cart.getRequiredLink(IanaLinkRelations.SELF).toUri() == selfUri
        cart.courses as Set == [course1] as Set
        cart.userId == loggedInUser.id
    }


    def "add two courses to cart: returns both courses"() {
        given:
        setupRestClient(aStudent())
        addCourseToCart(course1)
        addCourseToCart(course2)

        when:
        def response = rest.getForEntity(BASE_URL, CartModel)

        then:
        response.statusCode == HttpStatus.OK
        def cart = response.body
        def selfUri = new URI(appUrl + BASE_URL)
        cart.getRequiredLink(IanaLinkRelations.SELF).toUri() == selfUri
        cart.courses as Set == [course1, course2] as Set
        cart.userId == loggedInUser.id
    }

    private ResponseEntity<CartModel> addCourseToCart(UUID course) {
        rest.postForEntity(BASE_URL, new AddCourseToCartRequest(course), CartModel)
    }


    private UUID anyCourse() {
        def response = rest.postForEntity(CoursesController.BASE_URL, CreateCourseRequestBuilder.aCreateCourseRequest().build(), CourseModel)
        response.body.id
    }
}
