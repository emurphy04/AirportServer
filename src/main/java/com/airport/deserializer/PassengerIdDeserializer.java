package com.airport.deserializer;

import com.airport.model.Passenger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassengerIdDeserializer extends JsonDeserializer<List<Passenger>> {
    @Override
    public List<Passenger> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        List<Passenger> passengers = new ArrayList<>();

        if (node.isArray()) {
            for (JsonNode idNode : node) {
                Passenger passenger = new Passenger();
                passenger.setPassenger_id(idNode.asInt());
                passengers.add(passenger);
            }
        }
        return passengers;
    }
}