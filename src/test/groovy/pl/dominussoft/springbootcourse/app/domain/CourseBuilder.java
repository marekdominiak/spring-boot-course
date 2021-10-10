package pl.dominussoft.springbootcourse.app.domain;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public final class CourseBuilder {
    private UUID id = UUID.randomUUID();
    private String title = "title";
    // metadata -> could be a separate class (VO)
    private String description = "some description";
    private String duration = "duration";
    private Set<String> keywords = Set.of("keyword1", "keyword2");
    private Price price = new Price(BigDecimal.valueOf(1000), Currency.PLN);

    private CourseBuilder() {
    }

    public static CourseBuilder aCourse() {
        return new CourseBuilder();
    }

    public CourseBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public CourseBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CourseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CourseBuilder withDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public CourseBuilder withKeywords(Set<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public CourseBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    public Course build() {
        Course course = new Course(title, description, duration, keywords, price);
        course.setId(id);
        return course;
    }
}
