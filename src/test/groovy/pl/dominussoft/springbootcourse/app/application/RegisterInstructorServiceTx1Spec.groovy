package pl.dominussoft.springbootcourse.app.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository
import pl.dominussoft.springbootcourse.app.infrastructure.persistence.CustomInstructorRepositoryImpl
import spock.lang.Specification

@DataJdbcTest
// gives you -> @Transactional()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegisterInstructorServiceTx1Spec extends Specification {

    @Autowired
    InstructorRepository instructorRepository

    @Autowired
    NamedParameterJdbcOperations jdbc

    CustomInstructorRepositoryImpl customInstructorRepository

    RegisterInstructorService service

    def setup() {
        service = new RegisterInstructorService(instructorRepository)
        customInstructorRepository = new CustomInstructorRepositoryImpl(jdbc)
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

    def "handle: saves another instructor to db: Count of all instructors is 1"() {
        given:
        def createInstructor = createInstructorCmd()

        when:
        service.registerInstructor(createInstructor)

        then:
        instructorRepository.findAll().size() == 1
    }

    /**
     * Lab: Explain why this test passes. Should it pass or fail?
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

    private static RegisterInstructor createInstructorCmd() {
        def createInstructor = new RegisterInstructor(
                "Rick", "R.", "He will never gonna let you down.", [] as Set)
        createInstructor
    }
}
