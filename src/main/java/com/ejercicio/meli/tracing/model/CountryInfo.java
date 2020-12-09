package com.ejercicio.meli.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CountryInfo {
    private final List<CountryLanguage> countryLanguages;
    private final List<String> timezones;
    private final List<Integer> latlng;
    private final List<CountryInfoCurrency> currencies;

    public CountryInfo(@JsonProperty("languages") List<CountryLanguage> countryLanguages,
                       @JsonProperty("timezones") List<String> timezones,
                       @JsonProperty("latlng") List<Integer> latlng,
                       @JsonProperty("currencies") List<CountryInfoCurrency> currencies) {
        this.countryLanguages = countryLanguages;
        this.timezones = timezones;
        this.latlng = latlng;
        this.currencies = currencies;
    }

    public List<CountryLanguage> getCountryLanguages() {
        return countryLanguages;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public List<Integer> getLatlng() {
        return latlng;
    }

    public List<CountryInfoCurrency> getCurrencies() {
        return currencies;
    }
}
