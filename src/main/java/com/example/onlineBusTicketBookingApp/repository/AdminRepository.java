// PACKAGE
package com.example.onlineBusTicketBookingApp.repository;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ANNOTATION that marks this interface as a DAO (Data Access Object) for data access in Spring
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);
}