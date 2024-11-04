package com.airport.controller;

import com.airport.model.Airport;
import com.airport.model.City;
import com.airport.model.Flight;
import com.airport.service.AirportService;
import com.airport.service.CityService;
import com.airport.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController

public class AirportController {

    private final AirportService airportService;

    private final CityService cityService;

    private final FlightService flightService;


    @Autowired
    public AirportController(AirportService airportService, CityService cityService, FlightService flightService){
        this.airportService = airportService;
        this.cityService = cityService;
        this.flightService = flightService;
    }

    @PostMapping("/airports")
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport){
        Airport addedAirport = airportService.addAirport(airport);
        return new ResponseEntity<>(addedAirport, HttpStatus.CREATED);
    }

    @GetMapping("/airports")
    public List<Airport> getAirport(){
        return airportService.getAllAirports();
    }

    @GetMapping("/airports/{id}")
    public Optional<Airport> getAirportById(@PathVariable int id){
        return airportService.getAirportById(id);
    }

    @DeleteMapping("/airports/{id}")
    public void deleteAirport(@PathVariable int id){
        try {City city = cityService.getCityByAirport(id).get();
            city.setAirport(null);
        } catch (Exception e){
            System.out.println("No city associated.");
        }
        try {
            List<Flight> flights = flightService.getFlightByAirport(id);
            for (int i = 0; i < flights.size(); i++) {
                int flight_id = flights.get(i).getFlight_id();
                flightService.deleteFlight(flight_id);
            }
        } catch (Exception e){}
        airportService.deleteAirport(id);
    }

    @PutMapping("/airports/{id}")
    public Airport updateAirport(@PathVariable int id, @RequestBody Map<String, String>body){
        Airport curr = airportService.getAirportById(id).get();
        curr.setAirport_name(body.get("airport_name"));
        curr.setCode(body.get("code"));
        airportService.addAirport(curr);
        return curr;
    }
}
