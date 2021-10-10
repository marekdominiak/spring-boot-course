package pl.dominussoft.springbootcourse.app.domain.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
public abstract class Entity {

    @Getter
    private UUID id;


    public void setId(UUID id) {
        this.id = id;
    }
}
