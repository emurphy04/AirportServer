package com.airport.repository;

import com.airport.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepo extends JpaRepository<Passenger, Integer> {
}
