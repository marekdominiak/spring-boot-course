package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class Price {
    @NotNull
    @Min(0)
    BigDecimal amount;

    @NotBlank
    String currency;
}
