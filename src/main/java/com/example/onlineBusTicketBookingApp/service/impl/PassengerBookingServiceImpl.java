// PACKAGE
package com.example.onlineBusTicketBookingApp.service.impl;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.repository.BookingRepository;
import com.example.onlineBusTicketBookingApp.service.PassengerBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// ANNOTATIONS
@Service
public class PassengerBookingServiceImpl implements PassengerBookingService {

    private final BookingRepository bookingRepository;

    public PassengerBookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteBookingById(long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(long id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(existingBooking -> {
            // Updates fields of the existing appointment
            existingBooking.setDateOfBooking(updatedBooking.getDateOfBooking());
            existingBooking.setDateOfDeparture(updatedBooking.getDateOfDeparture());
            existingBooking.setDateOfArrival(updatedBooking.getDateOfArrival());
            existingBooking.setDateOfBooking(updatedBooking.getDateOfBooking());
            existingBooking.setNoOfSeats(updatedBooking.getNoOfSeats());
            existingBooking.setDeparture(updatedBooking.getDeparture());
            existingBooking.setDestination(updatedBooking.getDestination());
            existingBooking.setPassenger(updatedBooking.getPassenger());
            existingBooking.setAdmin(updatedBooking.getAdmin());
            return bookingRepository.save(existingBooking);
        }).orElse(null);
    }

    @Override
    public Optional<Booking> getBookingById(long id) {
        return Optional.ofNullable(bookingRepository.findById(id).orElse(null));
    }

    @Override
    public List<Booking> getBookingsByPassenger(Passenger passenger) {
        return bookingRepository.findByPassenger(passenger);
    }

    @Override
    public List<Booking> getBookingsByAdmin(Admin admin) {
        return bookingRepository.findByAdmin(admin);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
}