package pl.dominussoft.springbootcourse.app.infrastructure.web


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.MultiValueMap
import pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
trait BaseControllerV0Spec {

    @LocalServerPort
    int localServerPort

    String appUrl

    TestRestTemplate rest = new TestRestTemplate()

    @Autowired
    RestTemplateBuilder restTemplateBuilder

    @Autowired
    UserAccountRepository accountRepository

    @Autowired
    PasswordEncoder encoder
    // for discussion our own object mapper vs Spring one
    //    ObjectMapper obj = new ObjectMapper()

    def setupRestClient() {
        String rawPassword = "admin"
        def admin = UserAccountBuilder.anAdmin().withPassword(encoder.encode(rawPassword)).build()
        admin = accountRepository.save(admin)
        this.appUrl = "http://localhost:" + localServerPort
        def builder = restTemplateBuilder.rootUri(appUrl)
        this.rest = new TestRestTemplate(builder, admin.getEmail(), rawPassword, null)
    }


    MultiValueMap<String, String> defaultHeaders() {
        HttpHeaders headers = new HttpHeaders()
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        return headers
    }

    HttpEntity<String> emptyBody() {
        new HttpEntity<>(defaultHeaders())
    }
}
