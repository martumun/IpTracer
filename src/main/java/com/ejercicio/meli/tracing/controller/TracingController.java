package com.ejercicio.meli.tracing.controller;

import com.ejercicio.meli.tracing.exception.InvalidParamException;
import com.ejercicio.meli.tracing.exception.IpInfoNotFoundException;
import com.ejercicio.meli.tracing.model.StatsResponse;
import com.ejercicio.meli.tracing.model.TraceBody;
import com.ejercicio.meli.tracing.model.TracingResponse;
import com.ejercicio.meli.tracing.service.StatsService;
import com.ejercicio.meli.tracing.service.TracingService;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TracingController {

    @Autowired
    TracingService tracingService;
    @Autowired
    StatsService statsService;

    @PostMapping(path = "/tracer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TracingResponse> getIpInformation(@RequestBody TraceBody body) {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        if (!validator.isValid(body.getIp())) {
            throw new InvalidParamException("Ip in body is not valid");
        }
        return ResponseEntity.ok(tracingService.traceIp(body.getIp()));
    }

    @GetMapping("/tracer/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getTracingStats());
    }
}
