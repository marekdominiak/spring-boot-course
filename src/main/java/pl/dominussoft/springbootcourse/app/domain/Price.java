package pl.dominussoft.springbootcourse.app.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Price {
    BigDecimal amount;
    Currency currency;
}
