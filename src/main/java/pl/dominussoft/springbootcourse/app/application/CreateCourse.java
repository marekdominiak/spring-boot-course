package pl.dominussoft.springbootcourse.app.application;

import lombok.Builder;
import lombok.Value;
import pl.dominussoft.springbootcourse.app.domain.Currency;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Value
public class CreateCourse implements Command {
    String title;
    String description;
    String duration;
    Set<String> keywords;
    BigDecimal amount;
    // questionable if we should allow enum from domain in here -> for discussion
    Currency currency;

}
