package com.ejercicio.meli.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryInfoCurrency {
    private final String code;

    public CountryInfoCurrency(@JsonProperty("code") String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }
}
