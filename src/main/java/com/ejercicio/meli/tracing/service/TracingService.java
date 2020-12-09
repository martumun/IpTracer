package com.ejercicio.meli.tracing.service;

import com.ejercicio.meli.tracing.connector.CountryInfoConnector;
import com.ejercicio.meli.tracing.connector.CurrencyInfoConnector;
import com.ejercicio.meli.tracing.connector.IpTracerConnector;
import com.ejercicio.meli.tracing.db.MongoConnector;
import com.ejercicio.meli.tracing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TracingService {
    @Autowired
    private CountryInfoConnector countryInfoConnector;
    @Autowired
    private CurrencyInfoConnector currencyInfoConnector;
    @Autowired
    private IpTracerConnector ipTracerConnector;
    @Autowired
    private MongoConnector mongoConnector;

    private static final double BUENOS_AIRES_LAT = -34.603722;
    private static final double BUENOS_AIRES_LONG = -58.381592;

    public TracingResponse traceIp(String ip) {
        CurrencyInformation currencyInformation;
        String currencyCode;
        IpLocation ipLocation = ipTracerConnector.getIpLocation(ip);
        CountryInfo countryInfo = countryInfoConnector.getCountryInfo(ipLocation.getCountryCode3().isEmpty() ? ipLocation.getCountryCode() : ipLocation.getCountryCode3());
        List<CountryInfoCurrency> currencies = countryInfo.getCurrencies();

        if (!currencies.isEmpty()) {
            currencyCode = currencies.get(0).getCode();
            currencyInformation = currencyInfoConnector.getCurrencyInfo(currencyCode);
        } else {
            throw new RuntimeException("Could not find any currencies");
        }

        double distanceToBuenosAires = getDistanceToBuenosAires(countryInfo.getLatlng());

        // Save new country or update qty of calls
        saveOrUpdateDb(ipLocation.getCountryName(), distanceToBuenosAires);

        return new TracingResponse(ipLocation.getCountryName(), ipLocation.getCountryCode3(),
                getCurrentTimes(countryInfo.getTimezones()), distanceToBuenosAires, currencyCode,
                currencyInformation.getRates().get(currencyCode));
    }

    private void saveOrUpdateDb(String countryName, double distanceToBsAs) {
        CallsPerCountry callsPerCountry = mongoConnector.getCalls(countryName);
        if(callsPerCountry != null) {
            mongoConnector.updateCalls(callsPerCountry.addCall());
        } else {
            mongoConnector.addCall(new CallsPerCountry(countryName, distanceToBsAs));
        }
    }

    private List<String> getCurrentTimes(List<String> timezones) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return timezones.stream().map(t -> LocalTime.now(ZoneId.of(t)).format(dateTimeFormatter)).collect(Collectors.toList());
    }

    private double getDistanceToBuenosAires(List<Integer> latlng) {
        return distance(BUENOS_AIRES_LAT, BUENOS_AIRES_LONG, latlng.get(0), latlng.get(1), 'K');
    }

    // Function that calculates distance between 2 point (lat and long)
    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    //  This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //  This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
