// PACKAGE
package com.example.onlineBusTicketBookingApp.entity;

// IMPORTS
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// ANNOTATIONS
@Data // LOMBOK annotation to generate getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor // LOMBOK annotation to generate a constructor with all fields
@NoArgsConstructor // LOMBOK annotation to generate a no-argument constructor

@Entity // Annotation to define this class as a JPA entity (maps to a database table)
@Table(name = "bookings") // Annotation to specify the table name in the database
public class Booking {

    // Primary key for the Booking entity with auto-generation strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Date Of Booking, cannot be null
    @Column(nullable = false)
    private LocalDateTime dateOfBooking;

    // Date of Departure, cannot be null
    @Column(nullable = false)
    private LocalDateTime dateOfDeparture;

    // Date Of Arrival, cannot be null
    @Column(nullable = false)
    private LocalDateTime dateOfArrival;

    // Number Of Seats, cannot be null
    @Column(nullable = false)
    private Integer noOfSeats;

    // Departure location, cannot be null
    @Column(nullable = false)
    private String departure;

    // Arrival location, cannot be null
    @Column(nullable = false)
    private String destination;

    // Many-to-one relationship with Passenger, the 'fetch' strategy is lazy loading
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false) // Foreign key column for Passenger
    private Passenger passenger;

    // Many-to-one relationship with Admin, the 'fetch' strategy is lazy loading
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false) // Foreign key column for Admin
    private Admin admin;

    // Required CONSTRUCTOR
    public Booking(long l, String s, long l1, long l2) {

    }
}