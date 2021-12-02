package pl.dominussoft.springbootcourse.app.application.taxes;

import org.springframework.stereotype.Component;

@Component
public class NorwegianTaxCalc implements TaxCalc {

    @Override
    public int taxRate() {
        return 0;
    }

    @Override
    public boolean supports(String country) {
        return "norway".equals(country);
    }
}
