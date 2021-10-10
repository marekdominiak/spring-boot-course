package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Value
@NonFinal // because Hateos links wouldn't generate the class if onluy @Value was presented
public class CartModel extends RepresentationModel<CartModel> {
    // Not really needed - added to spark a discussion
    UUID id;
    UUID userId;
    List<UUID> courses;

}
