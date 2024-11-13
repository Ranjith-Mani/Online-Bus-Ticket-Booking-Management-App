// PACKAGE
package com.example.onlineBusTicketBookingApp;

//IMPORTS
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//ANNOTATIONS
@EntityScan(basePackages = "com.example.onlineBusTicketBookingApp.entity")
// Main application class for the Online Bus Ticket Booking Application
@SpringBootApplication
public class OnlineBusTicketBookingAppApplication {


	// Entry point of the Spring Boot application
	public static void main(String[] args) {
		// Launch the application
		SpringApplication.run(OnlineBusTicketBookingAppApplication.class, args);
	}
}