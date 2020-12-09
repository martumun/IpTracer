package com.ejercicio.meli.tracing.model;


public class CallsPerCountry {
    private final String country;
    private final double distance;
    private Long calls;


    public CallsPerCountry(String country, double distance) {
        this.country = country;
        this.distance = distance;
        this.calls = 1L;
    }

    public CallsPerCountry addCall() {
        this.calls += 1;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public double getDistance() {
        return distance;
    }

    public Long getCalls() {
        return calls;
    }
}
