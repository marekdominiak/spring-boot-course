package pl.dominussoft.springbootcourse.app.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.dominussoft.springbootcourse.app.domain.CourseAddedToCartEvent;

@Service
public class Listeners {

    @Autowired
    MailSender mailSender;
    @Autowired
    SmsSender smsSender;

    @EventListener
    public void listener1(CourseAddedToCartEvent event) {
        mailSender.send(" mail");
    }

    @EventListener
    public void listener2(CourseAddedToCartEvent event) {
        smsSender.send(" sms");
    }

    @TransactionalEventListener
    public void listener3(CourseAddedToCartEvent event) {
        System.out.println("After transaction!");
    }
}
