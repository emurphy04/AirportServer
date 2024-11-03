package com.airport.service;

import com.airport.model.Airport;
import com.airport.repository.AirportRepo;
import com.airport.repository.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {
    private final AirportRepo airportRepo;

    @Autowired
    public AirportService(AirportRepo airportRepo){
        this.airportRepo = airportRepo;
    }

    public Airport addAirport(Airport airport){
        return airportRepo.save(airport);
    }

    public List<Airport> getAllAirports(){
        return airportRepo.findAll();
    }

    public Optional<Airport> getAirportById(int id) {
        return airportRepo.findById(id);
    }

    public void deleteAirport(int id){
        airportRepo.deleteById(id);
    }
}
