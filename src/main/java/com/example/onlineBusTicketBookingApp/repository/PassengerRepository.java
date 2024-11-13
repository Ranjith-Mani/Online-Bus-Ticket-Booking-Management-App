// PACKAGE
package com.example.onlineBusTicketBookingApp.repository;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ANNOTATION
// Marks this interface as a DAO (Data Access Object) for data access in Spring
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByEmail(String email);
}