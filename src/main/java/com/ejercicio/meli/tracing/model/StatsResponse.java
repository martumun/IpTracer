package com.ejercicio.meli.tracing.model;

public class StatsResponse {
    private final double shortestDistanceToBuenosAires;
    private final double longestDistanceToBuenosAires;
    private final double averageDistanceToBuenosAires;

    public StatsResponse(double shortestDistanceToBuenosAires,
                         double longestDistanceToBuenosAires,
                         double averageDistanceToBuenosAires) {
        this.shortestDistanceToBuenosAires = shortestDistanceToBuenosAires;
        this.longestDistanceToBuenosAires = longestDistanceToBuenosAires;
        this.averageDistanceToBuenosAires = averageDistanceToBuenosAires;
    }

    public double getShortestDistanceToBuenosAires() {
        return shortestDistanceToBuenosAires;
    }

    public double getLongestDistanceToBuenosAires() {
        return longestDistanceToBuenosAires;
    }

    public double getAverageDistanceToBuenosAires() {
        return averageDistanceToBuenosAires;
    }
}
