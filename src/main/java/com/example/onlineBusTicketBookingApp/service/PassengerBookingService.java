// PACKAGE
package com.example.onlineBusTicketBookingApp.service;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.entity.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerBookingService {

    List<Booking> getAllBookings();

    boolean deleteBookingById(long id);

    Booking saveBooking(Booking booking);

    Booking updateBooking(long id, Booking updatedBooking);

    Optional<Booking> getBookingById(long id);

    List<Booking> getBookingsByPassenger(Passenger passenger);

    List<Booking> getBookingsByAdmin(Admin admin);
}