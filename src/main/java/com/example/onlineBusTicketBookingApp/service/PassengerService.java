// PACKAGE
package com.example.onlineBusTicketBookingApp.service;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Passenger;

import java.util.List;

public interface PassengerService {

    List<Passenger> getAllPassengers();

    Passenger getPassengerById(long id);

    Passenger savePassenger(Passenger passenger);

    Passenger updatePassenger(Long id, Passenger updatedPassenger);

    boolean deletePassengerById(long id);
}