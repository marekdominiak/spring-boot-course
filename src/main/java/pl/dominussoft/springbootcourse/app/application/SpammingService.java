package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpammingService {

    final MailSender mailSender;

    public SpammingService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSomeSpamMessages() {
        log.info("Sending spam messages!");
    }


}
