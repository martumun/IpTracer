package com.ejercicio.meli.tracing.connector;

import com.ejercicio.meli.tracing.exception.ConnectorException;
import com.ejercicio.meli.tracing.model.IpLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IpTracerConnector {
    private static final String IP_PATH = "/ip?%s";

    @Autowired
    private ObjectMapper mapper;


    public IpLocation getIpLocation(String ip) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://api.ip2country.info/" + String.format(IP_PATH, ip));

            IpLocation response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), IpLocation.class));
            return response;
        } catch (IOException e) {
            throw new ConnectorException(String.format("There was a problem getting ip's location: %s", e.getMessage()));
        }
    }

}
