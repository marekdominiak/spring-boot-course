package pl.dominussoft.springbootcourse.app.infrastructure.web

import org.springframework.http.HttpStatus
import spock.lang.Specification

import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.aStudent
import static pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder.anAdmin

class OrdersControllerSpec extends Specification implements BaseControllerV1Spec {

    UUID course1
    UUID course2

    def setup() {
        setupRestClient(anAdmin())
    }

    def "make order based on cart: order saved with two courses "() {
        given:
        course1 = anyCourse(CreateCourseRequestBuilder.aCreateCourseRequest().withPrice(pln(100)))
        course2 = anyCourse(CreateCourseRequestBuilder.aCreateCourseRequest().withPrice(pln(150)))

        setupRestClient(aStudent())

        addToCart(course1)
        addToCart(course2)

        when:
        def response = rest.postForEntity(OrdersController.BASE_URL, new PlaceOrderRequest(loggedInUser.id), CartModel)

        then:
        response.statusCode == HttpStatus.ACCEPTED
    }

    private void addToCart(UUID course) {
        rest.postForEntity(CartController.BASE_URL, new AddCourseToCartRequest(course), CartModel)
    }

    private UUID anyCourse(CreateCourseRequestBuilder request) {
        def response = rest.postForEntity(CoursesController.BASE_URL, request.build(), CourseModel)
        response.body.id
    }


    private static Price pln(Integer amount) {
        new Price(BigDecimal.valueOf(amount), "PLN")
    }

}
