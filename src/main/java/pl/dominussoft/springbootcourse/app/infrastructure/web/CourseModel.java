package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Value
@NonFinal // because Hateos links wouldn't generate the class if onluy @Value was presented
public class CourseModel extends RepresentationModel<CourseModel> {
    UUID id;
    String title;
    String description;
    String duration;
    Set<String> keywords;

}
