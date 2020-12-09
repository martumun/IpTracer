package com.ejercicio.meli.tracing.model;

import java.time.LocalTime;
import java.util.List;

public class TracingResponse {
    private final String countryName;
    private final String isoCode;
    private final List<LocalTime> currentTimes;
    private final double distanceToBuenosAires;
    private final String currency;
    private final Double euroExchangeRate;

    public TracingResponse(String countryName,
                           String isoCode,
                           List<LocalTime> currentTimes,
                           double distanceToBuenosAires,
                           String currency,
                           Double euroExchangeRate) {
        this.countryName = countryName;
        this.isoCode = isoCode;
        this.currentTimes = currentTimes;
        this.distanceToBuenosAires = distanceToBuenosAires;
        this.currency = currency;
        this.euroExchangeRate = euroExchangeRate;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public List<LocalTime> getCurrentTimes() {
        return currentTimes;
    }

    public double getDistanceToBuenosAires() {
        return distanceToBuenosAires;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getEuroExchangeRate() {
        return euroExchangeRate;
    }
}
