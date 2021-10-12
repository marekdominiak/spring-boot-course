package pl.dominussoft.springbootcourse.app.application.configuration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import pl.dominussoft.springbootcourse.app.infrastructure.persistence.DatabaseCleanerExtension

import java.lang.annotation.*

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest()
@TestExecutionListeners(
        value = [DatabaseCleanerExtension],
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@interface ServiceTest {

}