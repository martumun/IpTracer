package com.ejercicio.meli.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpLocation {
    private final String countryCode;
    private final String countryCode3;
    private final String countryName;

    public IpLocation(@JsonProperty("countryCode") String countryCode,
                      @JsonProperty("countryCode3") String countryCode3,
                      @JsonProperty("countryName") String countryName) {
        this.countryCode = countryCode;
        this.countryCode3 = countryCode3;
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryCode3() {
        return countryCode3;
    }

    public String getCountryName() {
        return countryName;
    }
}
