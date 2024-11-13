// PACKAGE
package com.example.onlineBusTicketBookingApp.service.impl;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.repository.PassengerRepository;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service implementation for PassengerService interface
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    // Constructor-based dependency injection
    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll(); // Retrieves all passenger records from the database
    }

    @Override
    public Passenger getPassengerById(long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        return passenger.orElse(null); // Retrieves a passenger by ID or returns null if not found
    }

    @Override
    public Passenger savePassenger(Passenger passenger) {
        if (passengerRepository.existsByEmail(passenger.getEmail())) {
            throw new IllegalArgumentException("Email already exists."); // Throws exception if email is already taken
        }
        passengerRepository.save(passenger); // Saves a new passenger record
        return passenger;
    }

    @Override
    public Passenger updatePassenger(Long id, Passenger updatedPassenger) {
        Optional<Passenger> existingPassengerOpt = passengerRepository.findById(id);
        if (existingPassengerOpt.isPresent()) {
            Passenger existingPassenger = existingPassengerOpt.get();
            // Update fields of existing passenger with the new values
            existingPassenger.setFirstName(updatedPassenger.getFirstName());
            existingPassenger.setLastName(updatedPassenger.getLastName());
            existingPassenger.setEmail(updatedPassenger.getEmail());
            existingPassenger.setPhoneNumber(updatedPassenger.getPhoneNumber());
            existingPassenger.setAddress(updatedPassenger.getAddress());

            return passengerRepository.save(existingPassenger); // Save updated passenger record
        }
        return null; // Return null if the passenger was not found
    }

    @Override
    public boolean deletePassengerById(long id) {
        if (passengerRepository.existsById(id)) {
            passengerRepository.deleteById(id); // Deletes passenger by ID
            return true;
        }
        return false; // Return false if the passenger was not found
    }
}