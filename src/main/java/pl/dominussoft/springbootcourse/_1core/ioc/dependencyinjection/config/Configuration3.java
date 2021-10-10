package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection.EchoService3;

@Configuration
public class Configuration3 {

    @Bean
    EchoService3 echo3() {
        return new EchoService3();
    }
}
