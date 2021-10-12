package pl.dominussoft.springbootcourse.app.application.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties("application.tax")
@Getter
@Setter
@Slf4j
public class TaxProperties {
    private int minThreshold;
    private int midThreshold;
    private int maxThreshold;

    @PostConstruct
    void test() {
        log.info("" + minThreshold);
    }
}
