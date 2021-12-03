package pl.dominussoft.springbootcourse.app.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class SchedulingConfiguration {
}
