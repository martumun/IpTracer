package com.ejercicio.meli.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class CurrencyInformation {
    private final HashMap<String, Double> rates;


    public CurrencyInformation(@JsonProperty("rates") HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }
}
