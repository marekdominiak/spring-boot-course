package pl.dominussoft.springbootcourse.app.application.configuration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import pl.dominussoft.springbootcourse.app.infrastructure.persistence.DatabaseCleanerExtension

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

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