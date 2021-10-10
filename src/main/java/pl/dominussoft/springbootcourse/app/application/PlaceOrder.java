package pl.dominussoft.springbootcourse.app.application;

import lombok.Value;

import java.util.UUID;

@Value
public class PlaceOrder implements Command {
    UUID userId;
}
