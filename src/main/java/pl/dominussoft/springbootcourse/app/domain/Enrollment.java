package pl.dominussoft.springbootcourse.app.domain;

import pl.dominussoft.springbootcourse.app.domain.base.AggregateRoot;

import java.time.LocalDate;

public class Enrollment extends AggregateRoot {

    UserAccount student;

    Course course;
    // assumed UTC-0

    LocalDate enrollmentDate;


    LocalDate validUntil;
}
