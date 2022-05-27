package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FooBarConfiguration {

    @Bean
    Bar bar() {
        log.info("bar()");
        return new Bar();
    }

    @Bean
    Foo foo() {
        log.info("foo()");
        return new Foo(bar());
    }

    @Bean
    Baz baz() {
        log.info("baz()");
        return new Baz(bar());
    }

    @Value
    static class Foo {
        Bar bar;
    }

    static class Bar {

    }

    @Value
    static class Baz {
        Bar bar;
    }
}
