package com.ejercicio.meli.tracing.connector;

import com.ejercicio.meli.tracing.exception.ConnectorException;
import com.ejercicio.meli.tracing.model.CountryInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CountryInfoConnector {
    private static final String COUNTRY_INFO_PATH = "/rest/v2/alpha/%s";

    @Autowired
    private ObjectMapper mapper;

    /**
     * Get's country information
     *
     * @param countryCode country's 3 letter code
     * @return An object containing this country's languages, timezones and location (latitude & longitude)
     * @see CountryInfo
     */
    public CountryInfo getCountryInfo(String countryCode) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://restcountries.eu" + String.format(COUNTRY_INFO_PATH, countryCode));

            CountryInfo response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), CountryInfo.class));
            return response;
        } catch (IOException e) {
            throw new ConnectorException(String.format("There was a problem getting country's info: %s", countryCode));
        }
    }
}
