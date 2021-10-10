package pl.dominussoft.springbootcourse.app.infrastructure.persistence


import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListener
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate

import javax.sql.DataSource

/**
 * Cleans database after before test.
 */
class DatabaseCleanerExtension implements TestExecutionListener {

    @Override
    void beforeTestMethod(TestContext testContext) throws Exception {

        def applicationContext = testContext.getApplicationContext()

        def dataSource = applicationContext.getBean(DataSource)
        def jdbc = new JdbcTemplate(dataSource)
        def txManager = applicationContext.getBean(PlatformTransactionManager)

        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager)
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbc.execute("SET REFERENTIAL_INTEGRITY FALSE")
                truncateAllTables(jdbc)
                jdbc.execute("SET REFERENTIAL_INTEGRITY TRUE")
            }
        })
    }

    private void truncateAllTables(JdbcTemplate jdbc) {
        def result = jdbc.queryForList("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'")
        result.each {
            row ->
                def tableName = row.values().iterator().next()
                jdbc.execute("TRUNCATE TABLE " + tableName)
        }
    }
}
