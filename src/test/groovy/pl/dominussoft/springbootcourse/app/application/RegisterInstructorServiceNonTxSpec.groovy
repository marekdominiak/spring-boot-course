package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@SpringBootTest
class RegisterInstructorServiceNonTxSpec extends Specification {

    @Autowired
    InstructorRepository instructorRepository


    RegisterInstructorService service

    def setup() {
        service = new RegisterInstructorService(instructorRepository)
    }

    def "handle: saves instructor to db"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        def id = service.registerInstructor(createInstructor)

        then:
        def found = instructorRepository.findById(id)
        found.isPresent()
        found.get().firstName == "Rick"
        found.get().id == id
    }

    /**
     * The test fail, but why?
     */
    def "handle: saves another instructor to db: Count of all instructors is 1"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        service.registerInstructor(createInstructor)

        then:
        instructorRepository.findAll().size() == 1
    }

    /**
     * The test fail, but why?
     */
    def "handle2: saves another instructor to db: Count of all instructors is 1"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        service.registerInstructorNoTx(createInstructor)

        then:
        instructorRepository.findAll().size() == 1
    }

    private static RegisterInstructor createInstructorCmd() {
        def createInstructor = new RegisterInstructor(
                "Rick", "R.", "He will never gonna let you down.", [] as Set)
        createInstructor
    }
}
