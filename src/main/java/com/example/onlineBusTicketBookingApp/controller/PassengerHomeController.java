// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ANNOTATION
@Controller // An ANNOTATION that tells Spring Boot that a class handles web requests and returns views (like HTML pages).
public class PassengerHomeController {
    // Mapping for the passenger home page
    @GetMapping("/passengers-home")
    public String showPassengersHomePage() {
        return "Passenger-home"; // Returns the Passengers-home view
    }
}