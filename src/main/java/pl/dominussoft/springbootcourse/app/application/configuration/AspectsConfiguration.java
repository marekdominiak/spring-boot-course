package pl.dominussoft.springbootcourse.app.application.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AspectsConfiguration {

    @Pointcut("within(pl.dominussoft.springbootcourse.app.infrastructure.web.*)")
    public void inWeb() {
    }

    @Pointcut("execution(public * *(..))")
    public void anyPublicOperation() {
    }

    @Around("anyPublicOperation() && inWeb()")
    public Object allControllerMethods(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Caught controller method {} with args {} ", pjp.getSignature(), pjp.getArgs());
        Object proceed = pjp.proceed();
        log.info("Now it's after the controller method {} ", pjp.getSignature());
        return proceed;
    }
}
