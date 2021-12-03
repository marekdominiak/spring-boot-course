package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Jacksonized
@Builder
@Value
public class Price {
    @NotNull
    @Min(0)
    BigDecimal amount;

    @NotBlank
    String currency;
}
