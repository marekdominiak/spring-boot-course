package pl.dominussoft.springbootcourse.app.domain.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@EqualsAndHashCode
public abstract class AggregateRoot {

    @Id
    @Getter
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }
}
