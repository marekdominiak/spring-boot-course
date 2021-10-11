package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Service
public class CoursesCacheService {

    @Autowired
    private CourseFinder finder;

    public CoursesCacheService() {
        log.info("CoursesCacheService: constructor: " + finder);
    }

    @PostConstruct
    public void initialize() {
        log.info("CoursesCacheService: @PostConstruct: " + finder);
    }

    public void doSomething() {
    }

    @PreDestroy
    public void destroy() {
        log.info("CoursesCacheService: @PreDestroy");
    }

}
