package pl.dominussoft.springbootcourse.app.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Embedded;
import pl.dominussoft.springbootcourse.app.domain.base.AggregateRoot;

import java.util.Set;

@ToString
@Getter
public class Course extends AggregateRoot {

    private String title;

    // metadata -> could be a separate class (VO)
    private String description;

    private String duration;

    private Set<String> keywords;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Price price;
//    private Integer daysValidFromEnrollment;

    public Course(String title, String description, String duration, Set<String> keywords, Price price) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.keywords = keywords;
        this.price = price;
    }
}
