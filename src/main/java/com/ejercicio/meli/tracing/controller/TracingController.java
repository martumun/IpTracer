package com.ejercicio.meli.tracing.controller;

import com.ejercicio.meli.tracing.model.StatsResponse;
import com.ejercicio.meli.tracing.model.TracingResponse;
import com.ejercicio.meli.tracing.service.StatsService;
import com.ejercicio.meli.tracing.service.TracingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TracingController {

    @Autowired
    TracingService tracingService;
    @Autowired
    StatsService statsService;

    @GetMapping("/tracer/ips/{ip}")
    public ResponseEntity<TracingResponse> getIpInformation(@PathVariable String ip) {
        return ResponseEntity.ok(tracingService.traceIp(ip));
    }

    @GetMapping("/tracer/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getTracingStats());
    }
}
