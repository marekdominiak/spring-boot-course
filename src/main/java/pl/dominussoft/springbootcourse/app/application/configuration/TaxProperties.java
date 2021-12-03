package pl.dominussoft.springbootcourse.app.application.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

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
