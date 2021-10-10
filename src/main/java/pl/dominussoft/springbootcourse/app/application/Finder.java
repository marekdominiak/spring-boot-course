package pl.dominussoft.springbootcourse.app.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * Markup annotation for Application Service classes. Provides transactions for all methods.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
@Transactional(readOnly = true)
@Inherited
@Documented
public @interface Finder {
}
