package pl.dominussoft.springbootcourse.app.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursesCacheService {

    @Autowired
    private CourseFinder finder;

    public void initialize() {
        System.out.println("Run after initialization");
    }

    public void doSomething() {
        System.out.println("Running");
    }

    public void destroy() {
        System.out.println("Run when closing");
    }

}
