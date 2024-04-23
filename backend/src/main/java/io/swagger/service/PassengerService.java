package io.swagger.service;

import io.swagger.model.Passenger;
import io.swagger.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getPassengerById(Integer passengerId) {
        return passengerRepository.findById(passengerId);
    }

    public Passenger createOrUpdatePassenger(Passenger passenger) {
        Integer maxIdFromDatabase = passengerRepository.findMaxPassengerId();
        int nextId = (maxIdFromDatabase != null) ? maxIdFromDatabase + 1 : 1;

        if (passenger.getPassengerId() == null) {
            passenger.setPassengerId(nextId);
        }
        return passengerRepository.save(passenger);
    }

    public void deletePassenger(Integer passengerId) {
        passengerRepository.deleteById(passengerId);
    }
}
