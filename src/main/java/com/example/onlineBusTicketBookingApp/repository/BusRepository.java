// PACKAGE
package com.example.onlineBusTicketBookingApp.repository;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Bus;
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// ANNOTATION
// Marks this interface as a DAO (Data Access Object) for Spring Data JPA
@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    List<Bus> findByPassenger(Passenger passenger);

    List<Bus> findByAdmin(Admin admin);
}