package com.airport.repository;

import com.airport.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepo extends JpaRepository<Airport, Integer> {

}
