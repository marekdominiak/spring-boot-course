package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository
import spock.lang.Ignore
import spock.lang.Specification

@Transactional
@SpringBootTest
// --> test execution listener OR Rollback
@Ignore
class RegisterInstructorServiceTxSpec extends Specification {

    @Autowired
    InstructorRepository instructorRepository

    @Autowired
    RegisterInstructorService service

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
        instructorRepository.findAll().size() == 1
    }

    def "handle: saves another instructor to db: Count of all instructors is 1"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        def instructorId = service.registerInstructor(createInstructor)

        then:
        instructorRepository.findAll().size() == 1
        instructorRepository.findById(instructorId).get() != null

    }

    /**
     * Lab: Explain why this test passes even though the @Transactional isn't on handle2 method.
     */
    def "handle2: saves another instructor to db: Count of all instructors is 1"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        service.registerInstructorNoTx(createInstructor)

        then:
        instructorRepository.findAll().size() == 1
    }


    /**
     * Why this test fails?
     */
    def "handleNoTxThrow: throws exception during and data should not be in the db"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        try {
            service.registerInstructorNoTxThrow(createInstructor)
        } catch (ignored) {
        }

        then:
        instructorRepository.findAll().size() == 0
    }

    private static RegisterInstructor createInstructorCmd() {
        def createInstructor = new RegisterInstructor(
                "Rick", "R.", "He will never gonna let you down.", [] as Set)
        createInstructor
    }
}
