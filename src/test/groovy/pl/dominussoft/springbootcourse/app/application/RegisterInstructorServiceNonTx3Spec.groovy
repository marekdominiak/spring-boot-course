package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository
import pl.dominussoft.springbootcourse.app.infrastructure.persistence.DatabaseCleanerExtension
import spock.lang.Specification

@SpringBootTest
@TestExecutionListeners(
        value = [DatabaseCleanerExtension],
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class RegisterInstructorServiceNonTx3Spec extends Specification {

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
    }


    /**
     * The test passes, but why?
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
     * The test passes, but why? Spring Data says: Gotcha!
     *    -> Discuss Implementation of SimpleJDBCRepository
     *    Is that a good thing or bad that SimpleJDBCRepository opens it's own transaction?
     */
    def "handle2: saves another instructor to db: Count of all instructors is 1"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        service.registerInstructorNoTx(createInstructor)

        then:
        instructorRepository.findAll().size() == 1
    }

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

    /**
     * Why this 100 tests run A LOT faster than the same test in RegisterInstructorHandlerNonTx2Spec?
     */
    def "handle2: saves instructor to db: Count of all instructors is 1: RUN 100 times: run #i"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        service.registerInstructor(createInstructor)

        then:
        instructorRepository.findAll().size() == 1

        where:
        i << (1..100)
    }

    private static RegisterInstructor createInstructorCmd() {
        def createInstructor = new RegisterInstructor(
                "Rick", "R.", "He will never gonna let you down.", [] as Set)
        createInstructor
    }
}
