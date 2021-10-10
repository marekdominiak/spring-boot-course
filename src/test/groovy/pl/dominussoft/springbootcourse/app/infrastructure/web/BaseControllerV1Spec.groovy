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
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.MultiValueMap
import pl.dominussoft.springbootcourse.app.domain.UserAccount
import pl.dominussoft.springbootcourse.app.domain.UserAccountBuilder
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository
import pl.dominussoft.springbootcourse.app.infrastructure.persistence.DatabaseCleanerExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestExecutionListeners(
        value = [DatabaseCleanerExtension],
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
trait BaseControllerV1Spec {

    @LocalServerPort
    int localServerPort

    @Autowired
    WebTestClient restClient

    @Autowired
    RestTemplateBuilder restTemplateBuilder

    @Autowired
    UserAccountRepository accountRepository

    @Autowired
    PasswordEncoder encoder

    String appUrl

    TestRestTemplate rest = new TestRestTemplate()

    // do we want objectmapper here?
//    ObjectMapper obj = new ObjectMapper()

    UserAccount loggedInUser

    def setupRestClient() {
        setupRestClient(UserAccountBuilder.anAdmin())
    }

    def setupRestClient(UserAccountBuilder user) {
        def rawPassword
        if (user.isWrongPassword()) {
            rawPassword = user.getPassword() + "_for_sure_its_wrong"
        } else {
            rawPassword = user.getPassword()
        }
        this.loggedInUser = user.withPassword(encoder.encode(user.getPassword())).build()
        this.loggedInUser = accountRepository.save(loggedInUser)
        this.appUrl = "http://localhost:" + localServerPort
        def builder = restTemplateBuilder.rootUri(appUrl)
        this.rest = new TestRestTemplate(builder, user.getEmail(), rawPassword, null)
    }

    def setupRestClientWithoutCredentials() {
        this.appUrl = "http://localhost:" + localServerPort
        def builder = restTemplateBuilder.rootUri(appUrl)
        this.rest = new TestRestTemplate(builder)
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
