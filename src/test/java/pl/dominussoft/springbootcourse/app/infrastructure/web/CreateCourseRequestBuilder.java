package pl.dominussoft.springbootcourse.app.infrastructure.web;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public final class CreateCourseRequestBuilder {
    String title = "course title";
    String description = "course description";
    String duration = "3 days";
    Set<String> keywords = new HashSet<>();
    Price price = new Price(BigDecimal.valueOf(1000), "PLN");

    private CreateCourseRequestBuilder() {
    }

    public static CreateCourseRequestBuilder aCreateCourseRequest() {
        return new CreateCourseRequestBuilder();
    }

    public CreateCourseRequestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CreateCourseRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateCourseRequestBuilder withDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public CreateCourseRequestBuilder withKeywords(Set<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public CreateCourseRequestBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    public CreateCourseRequest build() {
        return new CreateCourseRequest(title, description, duration, keywords, price);
    }
}
