package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailSender {

    public void send(String msg) {
        log.info("Email sent: {}", msg);
    }

}
