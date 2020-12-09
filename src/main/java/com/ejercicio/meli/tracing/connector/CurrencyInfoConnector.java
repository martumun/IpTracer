package com.ejercicio.meli.tracing.connector;

import com.ejercicio.meli.tracing.exception.ConnectorException;
import com.ejercicio.meli.tracing.model.CurrencyInformation;
import com.ejercicio.meli.tracing.model.IpLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class CurrencyInfoConnector {

    private static final String CURRENCY_INFO_PATH = "/api/latest?access_key=%s&format=1&symbols=%s";

    @Autowired
    private ObjectMapper mapper;

    public CurrencyInformation getCurrencyInfo(String currencyCode) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("http://data.fixer.io" + String.format(CURRENCY_INFO_PATH, "c1f4f943ed9b79055d044139fc59c19c",currencyCode));

            CurrencyInformation response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), CurrencyInformation.class));
            return response;
        } catch (IOException e) {
            throw new ConnectorException(String.format("There was a problem getting currency rates: %s", currencyCode));
        }
    }
}
