package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder
@Jacksonized
public class PlaceOrderRequest {

    @Getter
    UUID id;

    public PlaceOrderRequest(UUID id) {
        this.id = id;
    }
}
