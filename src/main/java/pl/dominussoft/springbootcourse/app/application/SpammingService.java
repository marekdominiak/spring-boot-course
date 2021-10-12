package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpammingService {

    @Autowired
    MailSender mailSender;

    @Scheduled(fixedDelay = 5000)
    public void sendSomeSpamMessages() {
        log.info("Sending spam messages!");
    }


}
