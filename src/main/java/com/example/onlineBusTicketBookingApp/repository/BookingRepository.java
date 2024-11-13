// PACKAGE
package com.example.onlineBusTicketBookingApp.repository;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ANNOTATION
// Marks this interface as a DAO (Data Access Object) for Spring Data JPA
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPassenger(Passenger passenger);

    List<Booking> findByAdmin(Admin admin);
}