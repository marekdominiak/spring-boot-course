package pl.dominussoft.springbootcourse.app.application.configuration;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Configuration;
import pl.dominussoft.springbootcourse.app.application.OrderFinder;

@Configuration
public class BeansConfiguration {

    OrderFinder orderFinder() {
        throw new NotImplementedException();
    }

}
