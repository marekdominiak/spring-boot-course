package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder
@Jacksonized
@Value
public class AddCourseToCartRequest {
    @NonNull
    UUID courseId;
}

