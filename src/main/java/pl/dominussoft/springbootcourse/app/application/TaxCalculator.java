package pl.dominussoft.springbootcourse.app.application;

import lombok.extern.slf4j.Slf4j;
import pl.dominussoft.springbootcourse.app.domain.Order;

@Slf4j
//@Service
public class TaxCalculator {

    private final int taxPercent;

    public TaxCalculator(int taxPercent) {
        this.taxPercent = taxPercent;
    }

    public int calculateTaxPercent(Order order) {
        log.info("Calculated {} tax to {}", taxPercent, order);
        return taxPercent;
    }
}
