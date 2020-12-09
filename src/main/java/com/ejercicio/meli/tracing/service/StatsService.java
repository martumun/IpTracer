package com.ejercicio.meli.tracing.service;

import com.ejercicio.meli.tracing.db.MongoConnector;
import com.ejercicio.meli.tracing.model.StatsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    @Autowired
    private MongoConnector mongoConnector;


    public StatsResponse getTracingStats() {
        return new StatsResponse(mongoConnector.getShortestDistance(),
                mongoConnector.getLongestDistance(), mongoConnector.getAverageDistance());
    }
}
