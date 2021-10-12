package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import pl.dominussoft.springbootcourse.app.domain.base.AggregateRoot;
import pl.dominussoft.springbootcourse.app.domain.base.Entity;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * Class configuring Spring Data JDBC - here H2 is used for our convenience.
 */
@Configuration
@EnableJdbcRepositories(basePackages = {"pl.dominussoft.springbootcourse.app"})
public class DataStorageConfiguration extends AbstractJdbcConfiguration {

    public static final int FIVE_MINUTES = 60 * 5;
    @Autowired
    private DataSource dataSource;

    /**
     * As you can see Spring DATA Jdbc uses JDBC Operations (aka interface implemented by JdbcTemplate)
     */
    @Bean
    NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource());
    }


    @Bean
    PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
        dataSourceTransactionManager.setDefaultTimeout(FIVE_MINUTES);
        return dataSourceTransactionManager;
    }

    //    @Bean
    DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .generateUniqueName(true)
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("schema.sql")
//                .build();
        return dataSource;
    }

    @Bean
    public ApplicationListener<BeforeSaveEvent<?>> idGenerator() {
        // This is how you override ID generation in Spring data JDBC
        // A bit ugly. If we want to send UUID from outside we would have to declare
        // a transient field with e.g. UUID customId; in AggregateRoot / Entity and then
        // use it in this method. It can become handy when dealing with asynchronous systems.
        return event -> {
            var entity = event.getEntity();
            if (entity instanceof AggregateRoot) {
                AggregateRoot entity1 = (AggregateRoot) entity;
                if (entity1.getId() == null) {
                    entity1.setId(UUID.randomUUID());
                }
            }
            if (entity instanceof Entity) {
                Entity entity1 = (Entity) entity;
                if (entity1.getId() == null) {
                    entity1.setId(UUID.randomUUID());
                }
            }
        };
    }

}
