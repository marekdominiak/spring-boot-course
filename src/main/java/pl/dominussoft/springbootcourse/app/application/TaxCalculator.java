package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.dominussoft.springbootcourse.app.domain.Order;

@Slf4j
@Service
public class TaxCalculator {

    private final int taxPercent;

    public TaxCalculator(@Value("${application.tax.percent:30}") int taxPercent) {
        this.taxPercent = taxPercent;
    }

    public int calculateTaxPercent(Order order) {
        log.info("Calculated {} tax to {}", taxPercent, order);
        return taxPercent;
    }
}
