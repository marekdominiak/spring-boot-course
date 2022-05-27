package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Profile("dev")
@Component
@Slf4j
public class AsyncExampleCommandInitializer implements CommandLineRunner {

    private final ImportantService importantService;

    public AsyncExampleCommandInitializer(ImportantService importantService) {
        this.importantService = importantService;
    }

    @Override
    public void run(String... args) {
        log.info("Start Async Test:");
//        asyncMethod();
        //importantService.veryLongOperation();
        log.info("End Async Test:");
    }


//    @Async
//    public void asyncMethod() {
//        try {
//            Thread.sleep(10000);
//            log.info("Async from another bean");
//        } catch (InterruptedException e) {
//        }
//    }


    @Service
    static class ImportantService {
        public void veryLongOperation() {
            try {
                Thread.sleep(20000);
                System.out.println("Async from another bean");
            } catch (InterruptedException e) {
            }
        }
    }
}
