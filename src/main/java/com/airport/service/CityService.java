package com.airport.service;

import com.airport.model.Airport;
import com.airport.model.City;
import com.airport.repository.AirportRepo;
import com.airport.repository.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepo cityRepo;
    private final AirportRepo airportRepo;

    @Autowired
    public CityService(CityRepo cityRepo, AirportRepo airportRepo){
        this.cityRepo = cityRepo;
        this.airportRepo = airportRepo;
    }

    public City addCity(City city, int airport_id){
        Airport airport = airportRepo.findById(airport_id)
                .orElseThrow(() -> new IllegalArgumentException("Airport not found"));

        city.setAirport(airport);
        return cityRepo.save(city);
    }

    public List<City> getAllCities(){
        return cityRepo.findAll();
    }

    public void deleteCity(int id) {
        cityRepo.deleteById(id);
    }

    public Optional<City> getCityByAirport(int airport_id){
        List<City> cities = getAllCities();
        for (int i = 0; i < cities.size(); i++) {
            Airport possibleAirport = cities.get(i).getAirport();
            if (possibleAirport.getAirport_id() == airport_id){
                return Optional.ofNullable(cities.get(i));
            }
        }
        return Optional.empty();
    }

    public Optional<City> getCityById(int id) {
        return cityRepo.findById(id);
    }
}

