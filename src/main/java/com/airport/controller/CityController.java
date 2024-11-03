package com.airport.controller;

import com.airport.model.Airport;
import com.airport.model.City;
import com.airport.service.AirportService;
import com.airport.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController

public class CityController {

    private final CityService cityService;

    private final AirportService airportService;

    @Autowired
    public CityController(CityService cityService, AirportService airportService){
        this.cityService = cityService;
        this.airportService = airportService;
    }

    @PostMapping("/cities")
    public ResponseEntity<City> createCity(@RequestBody Map<String, Object> payload){
        String name = (String) payload.get("name");
        int population = (int) payload.get("population");
        String province = (String) payload.get("province");
        int airport_id = (int) payload.get("airport_id");

        City city = new City();
        city.setName(name);
        city.setPopulation(population);
        city.setProvince(province);

        City addedCity = cityService.addCity(city, airport_id);
        return new ResponseEntity<>(addedCity, HttpStatus.CREATED);
    }

    @GetMapping("/cities")
    public List<City> getCities(){
        return cityService.getAllCities();
    }

    @GetMapping("/cities/{id}")
    public Optional<City> getCityById(@PathVariable int id){
        return cityService.getCityById(id);
    }

    @GetMapping("/cities/airports/{id}")
    public Optional<City> getCityByAirport(@PathVariable int id){
        return cityService.getCityByAirport(id);
    }

    @DeleteMapping("/cities/{id}")
    public void deleteCity(@PathVariable int id){
        cityService.deleteCity(id);
    }

    @PutMapping("/cities/{id}")
    public City updateCity(@PathVariable int id, @RequestBody Map<String, String> body){
        City curr = cityService.getCityById(id).get();
        curr.setName(body.get("name"));
        curr.setProvince(body.get("province"));
        curr.setPopulation(Integer.parseInt(body.get("population")));
        curr.setAirport(airportService.getAirportById(Integer.parseInt(body.get("airport_id"))).get());

        cityService.addCity(curr, Integer.parseInt(body.get("airport_id")));

        return curr;
    }
}
