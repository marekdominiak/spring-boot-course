package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.dominussoft.springbootcourse.app.domain.Order;

@Slf4j
@Service
public class TaxCalculator {

    private final int taxPercent;
    private final String taxRateAsString;

    public TaxCalculator(@Value("${application.tax.percent:30}") int taxPercent,
                         @Value("${application.tax.percent.string:}") String taxRateAsString) {
        this.taxPercent = taxPercent;
        this.taxRateAsString = taxRateAsString;
    }

    public int calculateTaxPercent(Order order) {
        log.info("Calculated {} tax to {}", taxPercent, order);
        return taxPercent;
    }

    public String presentTaxRate() {
        return taxRateAsString;
    }

}
