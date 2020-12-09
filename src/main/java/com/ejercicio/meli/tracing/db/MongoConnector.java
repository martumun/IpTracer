package com.ejercicio.meli.tracing.db;

import com.ejercicio.meli.tracing.exception.JsonParsingException;
import com.ejercicio.meli.tracing.model.CallsPerCountry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ejercicio.meli.tracing.util.FieldsLiterals.COUNTRY_NAME;
import static com.ejercicio.meli.tracing.util.FieldsLiterals.DISTANCE;
import static com.mongodb.client.model.Filters.eq;

@Service
public class MongoConnector {

    private MongoClient mongoClient;
    @Autowired
    private ObjectMapper mapper;
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> callsCollection;


    public MongoConnector() {
        mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase("tracing");
        callsCollection = mongoDatabase.getCollection("calls");
    }

    public CallsPerCountry getCalls(String countryName) {
        Document doc = callsCollection.find(eq(COUNTRY_NAME, countryName)).first();
        if(doc != null) {
            try {
                return mapper.readValue(doc.toJson(), CallsPerCountry.class);
            } catch (JsonProcessingException e) {
                throw new JsonParsingException(e.getMessage());
            }
        } else {
            return null;
        }
    }

    public void addCall(CallsPerCountry callsPerCountry) {
        try {
            callsCollection.insertOne(Document.parse(mapper.writeValueAsString(callsPerCountry)));
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e.getMessage());
        }
    }

    public Double getLongestDistance() {
        Document doc = callsCollection.find().sort(eq(DISTANCE, -1)).limit(1).first();
        if (doc != null) {
            try {
                return mapper.readValue(doc.toJson(), CallsPerCountry.class).getDistance();
            } catch (JsonProcessingException e) {
                throw new JsonParsingException(e.getMessage());
            }
        } else {
            return null;
        }
    }

    public Double getShortestDistance() {
        Document doc = callsCollection.find().sort(eq(DISTANCE, 1)).limit(1).first();
        if (doc != null) {
            try {
                return mapper.readValue(doc.toJson(), CallsPerCountry.class).getDistance();
            } catch (JsonProcessingException e) {
                throw new JsonParsingException(e.getMessage());
            }
        } else {
            return null;
        }
    }

    public Double getAverageDistance() {
        long totalCalls = 0;
        double average = 0;
        List<Document> allCalls = callsCollection.find().into(new ArrayList<>());
        for (Document c: allCalls) {
            try {
                CallsPerCountry callsPerCountry = mapper.readValue(c.toJson(), CallsPerCountry.class);
                totalCalls += callsPerCountry.getCalls();
                average += callsPerCountry.getDistance() * callsPerCountry.getCalls();
            } catch (JsonProcessingException e) {
                throw new JsonParsingException(e.getMessage());
            }
        }

        if(totalCalls != 0) {
            return average / totalCalls;
        } else {
            return 0d;
        }
    }

    public void updateCalls(CallsPerCountry callsPerCountry) {
        try {
            callsCollection.updateOne(eq("countryName", callsPerCountry), Document.parse(mapper.writeValueAsString(callsPerCountry)));
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e.getMessage());
        }
    }


}
