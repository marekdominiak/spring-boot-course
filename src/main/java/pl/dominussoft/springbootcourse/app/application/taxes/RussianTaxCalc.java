package pl.dominussoft.springbootcourse.app.application.taxes;

import org.springframework.stereotype.Component;

@Component
public class RussianTaxCalc implements TaxCalc {

    @Override
    public int taxRate() {
        return 0;
    }

    @Override
    public boolean supports(String country) {
        return "russia".equals(country);
    }
}
