// PACKAGE
package com.example.onlineBusTicketBookingApp.service.impl;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Bus;
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.exception.NoAdminsOrPassengersException;
import com.example.onlineBusTicketBookingApp.repository.AdminRepository;
import com.example.onlineBusTicketBookingApp.repository.BusRepository;
import com.example.onlineBusTicketBookingApp.repository.PassengerRepository;
import com.example.onlineBusTicketBookingApp.service.PassengerBusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// ANNOTATIONS
@Service
public class PassengerBusServiceImpl implements PassengerBusService {

    private final BusRepository busRepository;
    private final AdminRepository adminRepository;
    private final PassengerRepository passengerRepository;

    public PassengerBusServiceImpl(BusRepository busRepository, AdminRepository adminRepository, PassengerRepository passengerRepository) {
        this.busRepository = busRepository;
        this.adminRepository = adminRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public boolean deleteBusById(long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public Bus updateBus(long id, Bus updatedBus) {
        Optional<Bus> existingBus = busRepository.findById(id);
        if (existingBus.isPresent()) {
            Bus bus = existingBus.get();
            // Updating fields with the values from updatedMedication
            bus.setBusNumber(updatedBus.getBusNumber());
            bus.setBusDriverName(updatedBus.getBusDriverName());
            bus.setBusType(updatedBus.getBusType());
            bus.setRoutes(updatedBus.getRoutes());
            bus.setAvailableSeats(updatedBus.getAvailableSeats());
            bus.setPassenger(updatedBus.getPassenger());
            bus.setAdmin(updatedBus.getAdmin());

            return busRepository.save(bus);
        }
        return null;
    }

    @Override
    public Bus getBusById(long id) {
        return busRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bus> getBusesByPassenger(Passenger passenger) {
        return busRepository.findByPassenger(passenger);
    }

    @Override
    public List<Bus> getBusesByAdmin(Admin admin) {
        return busRepository.findByAdmin(admin);
    }

    public void assignBusToPassenger() {
        List<Admin> admins = adminRepository.findAll();
        List<Passenger> passengers = passengerRepository.findAll();

        if (admins.isEmpty() || passengers.isEmpty()) {
            throw new NoAdminsOrPassengersException("No admins or passengers available to assign bus.");
        }
    }
}