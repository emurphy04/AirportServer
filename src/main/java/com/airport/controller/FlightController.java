package com.airport.controller;

import com.airport.model.Airport;
import com.airport.model.Flight;
import com.airport.model.Passenger;
import com.airport.service.AirportService;
import com.airport.service.FlightService;
import com.airport.service.PassengerService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Map<String, Object> payload) {
        // Create a new Flight instance
        Flight flight = new Flight();
        flight.setFlightNumber((String) payload.get("flightNumber"));

        // Retrieve origin and destination IDs from the payload
        Integer originAirportId = (Integer) payload.get("origin_airport_id");
        Integer destinationAirportId = (Integer) payload.get("destination_airport_id");

        // Extract passenger IDs from payload
        List<Integer> passengerIds = (List<Integer>) payload.get("passengers");

        // Call the service, passing Flight and separate ID values
        Flight createdFlight = flightService.createFlight(flight, originAirportId, destinationAirportId, passengerIds);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Flight> getAllFlights(){
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable int id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/passenger/{id}")
    public ResponseEntity<Flight> getFlightByPassenger(@PathVariable int id) {
        Optional<Flight> flight = Optional.ofNullable(flightService.getFlightByPassenger(id));
        return flight.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void cancelFlight(@PathVariable int id){
        flightService.deleteFlight(id);
    }

    @PutMapping("/{flight_id}")
    public Flight updateFlight(@PathVariable int flight_id, @RequestBody Map<String, Object> body) {
        // Retrieve the current Flight entity by ID
        Flight curr = flightService.getFlightById(flight_id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        // Update basic fields
        curr.setFlightNumber((String) body.get("flightNumber"));

        // Extract IDs for origin, destination, and passengers
        Integer originAirportId = (Integer) body.get("origin_airport_id");
        Integer destinationAirportId = (Integer) body.get("destination_airport_id");
        List<Integer> passengerIds = (List<Integer>) body.get("passengers");

        // Use createFlight with all required arguments for consistency
        return flightService.createFlight(curr, originAirportId, destinationAirportId, passengerIds);
    }
}

