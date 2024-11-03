package com.airport.repository;

import com.airport.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepo extends JpaRepository<Flight, Integer> {
}
