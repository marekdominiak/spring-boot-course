package pl.dominussoft.springbootcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import pl.dominussoft.springbootcourse.app.infrastructure.persistence.DataStorageConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Import(DataStorageConfiguration.class)
@EnableAspectJAutoProxy
public class SpringBootCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCourseApplication.class, args);
	}

}
