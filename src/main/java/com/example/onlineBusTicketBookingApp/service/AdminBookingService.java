// PACKAGE
package com.example.onlineBusTicketBookingApp.service;

// IMPORTS

import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.entity.Passenger;

import java.util.List;

public interface AdminBookingService {

    List<Booking> getAllBookings();

    boolean deleteBookingById(long id);

    Booking saveBooking(Booking booking);

    Booking updateBooking(long id, Booking updatedBooking);

    Booking getBookingById(long id);

    List<Booking> getBookingsByPassenger(Passenger passenger);

    List<Booking> getBookingsByAdmin(Admin admin);
}