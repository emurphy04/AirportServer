package com.airport.service;

import com.airport.model.Airport;
import com.airport.model.Flight;
import com.airport.model.Passenger;
import com.airport.repository.AirportRepo;
import com.airport.repository.FlightRepo;
import com.airport.repository.PassengerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepo flightRepo;

    @Autowired
    private AirportRepo airportRepo;

    @Autowired
    private PassengerRepo passengerRepo;

    public Flight createFlight(Flight flight, Integer originAirportId, Integer destinationAirportId, List<Integer> passengerIds) {
        // Retrieve and set the origin and destination airports
        Airport origin = airportRepo.findById(originAirportId)
                .orElseThrow(() -> new RuntimeException("Origin airport not found"));
        Airport destination = airportRepo.findById(destinationAirportId)
                .orElseThrow(() -> new RuntimeException("Destination airport not found"));
        flight.setOrigin(origin);
        flight.setDestination(destination);

        // Clear existing passengers (if updating) and set new ones based on provided IDs
        flight.delPassengers();
        List<Passenger> passengers = passengerIds.stream()
                .map(id -> passengerRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Passenger not found for ID: " + id)))
                .toList();
        flight.setPassengers(passengers);

        // Save and return the populated or updated Flight
        return flightRepo.save(flight);
    }

    public Optional<Flight> getFlightById(int flightId) {
        return flightRepo.findById(flightId);
    }

    public void deleteFlight(int id){
        flightRepo.deleteById(id);
    }

    public List<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public List<Flight> getFlightByAirport(int airport_id){
        List<Flight> flights = getAllFlights();
        List<Flight> flightsWithAirport = new java.util.ArrayList<>(List.of());
        for (Flight flight : flights) {
            Airport possibleOrigin = flight.getOrigin();
            Airport possibleDestination = flight.getDestination();
            if (possibleOrigin.getAirport_id() == airport_id || possibleDestination.getAirport_id() == airport_id) {
                flightsWithAirport.add(flight);
            }
        }
        return flightsWithAirport;
    }

    public Flight getFlightByPassenger(int passenger_id){
        List<Flight> flights = getAllFlights();
        for (Flight flight : flights) {
            List<Passenger> passengers = flight.getPassengers();
            for (Passenger passenger : passengers) {
                if (passenger.getPassenger_id() == passenger_id) {
                    return flight;
                }
            }
        }
        return null;
    }
}

