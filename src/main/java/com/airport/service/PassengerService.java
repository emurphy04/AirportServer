package com.airport.service;

import com.airport.model.Passenger;
import com.airport.repository.PassengerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    private final PassengerRepo passengerRepo;

    @Autowired
    public PassengerService(PassengerRepo passengerRepo) {this.passengerRepo = passengerRepo;}

    public Passenger addPassenger(Passenger passenger){return passengerRepo.save(passenger);}

    public List<Passenger> getAllPassengers(){return passengerRepo.findAll();}

    public Optional<Passenger> getPassengerById(int id){ return passengerRepo.findById(id);}

    public void deletePassenger(int id) {
        passengerRepo.deleteById(id);
    }
}
