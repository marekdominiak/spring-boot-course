package pl.dominussoft.springbootcourse.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    PLN("PLN");

    String currencyCode;
}