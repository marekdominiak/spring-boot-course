package pl.dominussoft.springbootcourse.app.application.taxes;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaxesManager {

    final List<TaxCalc> taxCalcs;

    public TaxesManager(List<TaxCalc> taxCalcs) {
        this.taxCalcs = taxCalcs;
    }

    int taxRateFor(String country) {

        TaxCalc calc = taxCalcs.stream()
                .filter(taxCalc -> taxCalc.supports(country))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Tax rates for %s isn't supported", country)));
        return calc.taxRate();
    }
}
