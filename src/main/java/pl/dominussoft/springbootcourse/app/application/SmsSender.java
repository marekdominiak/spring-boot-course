package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsSender {

    public void send(String msg) {
        log.info("Sms sent: {}", msg);
    }

}
