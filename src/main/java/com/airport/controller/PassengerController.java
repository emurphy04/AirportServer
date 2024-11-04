package com.airport.controller;

import com.airport.model.Flight;
import com.airport.model.Passenger;
import com.airport.service.AirportService;
import com.airport.service.FlightService;
import com.airport.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PassengerController {

    private final PassengerService passengerService;

    private final FlightService flightService;

    @Autowired
    public PassengerController(PassengerService passengerService, FlightService flightService) {
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    @PostMapping("/passengers")
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger){
        Passenger addedPassenger = passengerService.addPassenger(passenger);
        return new ResponseEntity<>(addedPassenger, HttpStatus.CREATED);
    }

    @GetMapping("/passengers")
    public List<Passenger> getAllPassengers(){
        return passengerService.getAllPassengers();
    }

    @GetMapping("/passengers/{id}")
    public Optional<Passenger> getPassengerById(@PathVariable int id){
        return passengerService.getPassengerById(id);
    }

    @DeleteMapping("/passengers/{id}")
    public void deletePassenger(@PathVariable int id){
        try {
            Flight flight = flightService.getFlightByPassenger(id);
            List<Passenger> passengers = flight.getPassengers();
            for (int i = 0; i < passengers.size(); i++) {
                try {
                    if (passengers.get(i).getPassenger_id() == id) {
                        passengers.remove(i);
                    }
                } catch (Exception e) {
                    System.out.println("passenger not found");
                }
            }
            flight.setPassengers(passengers);
        } catch (Exception e){
            System.out.println("Passenger is not on existing flight, safe to delete.");
        }
        passengerService.deletePassenger(id);
    }

    @PutMapping("/passengers/{id}")
    public Passenger updatePassenger(@PathVariable int id, @RequestBody Map<String, String> body){
        Passenger curr = passengerService.getPassengerById(id).get();
        curr.setFirst_name(body.get("first_name"));
        curr.setLast_name(body.get("last_name"));
        curr.setPhone_number(body.get("phone_number"));
        passengerService.addPassenger(curr);
        return curr;
    }
}
