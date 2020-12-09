package com.ejercicio.meli.tracing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TraceBody {
    private final String ip;

    public TraceBody(@JsonProperty("ip") String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}
