package pl.dominussoft.springbootcourse.app.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Embedded;
import pl.dominussoft.springbootcourse.app.domain.base.Entity;

import java.util.UUID;

@ToString
@Getter
public class OrderLine extends Entity {

    UUID courseId;
    String comment;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    Price price;

    public OrderLine(UUID courseId, String comment, Price price) {
        setId(UUID.randomUUID());
        this.courseId = courseId;
        this.comment = comment;
        this.price = price;
    }
}
