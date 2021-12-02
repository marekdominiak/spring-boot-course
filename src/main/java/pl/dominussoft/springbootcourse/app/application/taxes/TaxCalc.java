package pl.dominussoft.springbootcourse.app.application.taxes;

public interface TaxCalc {

    int taxRate();

    boolean supports(String country);

}
