package com.ejercicio.meli.tracing.connector;

import com.ejercicio.meli.tracing.exception.ConnectorException;
import com.ejercicio.meli.tracing.exception.IpInfoNotFoundException;
import com.ejercicio.meli.tracing.model.IpLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IpTracerConnector {
    private static final String IP_PATH = "/ip?%s";

    @Autowired
    private ObjectMapper mapper;

    @Value("${iptracer.path}")
    private String host;

    /**
     * Get ip information
     *
     * @param ip ip address
     * @return An object containing this ip's country code and name
     * @see IpLocation
     */
    public IpLocation getIpLocation(String ip) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(host + String.format(IP_PATH, ip));

            IpLocation response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), IpLocation.class));

            // to prevent unhandled exceptions later
            if(response.getCountryCode3().isEmpty() && response.getCountryCode().isEmpty()) {
                throw new IpInfoNotFoundException("Info for ip " + ip + " could not be found");
            }
            return response;
        } catch (IOException e) {
            throw new ConnectorException(String.format("There was a problem getting ip's location: %s", e.getMessage()));
        }
    }

}
