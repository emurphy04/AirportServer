package com.airport.controller;

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
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.createFlight(flight);
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
    public Flight updateFlight(@PathVariable int flight_id, @RequestBody Map<String, Object> body){
        Flight curr = flightService.getFlightById(flight_id).get();
        curr.setFlightNumber((String) body.get("flightNumber"));
        curr.setOrigin(airportService.getAirportById((Integer) body.get("origin_airport_id")).get());
        curr.setDestination(airportService.getAirportById((Integer) body.get("destination_airport_id")).get());
        curr.delPassengers();
        List<Integer> passenger_ids = (List<Integer>) body.get("passengers");
        List<Passenger> passengers = new ArrayList<>();
        for (Integer passengerId : passenger_ids) {
            passengers.add(passengerService.getPassengerById(passengerId).get());
        }
        curr.setPassengers(passengers);
        flightService.createFlight(curr);
        return curr;
    }
}

