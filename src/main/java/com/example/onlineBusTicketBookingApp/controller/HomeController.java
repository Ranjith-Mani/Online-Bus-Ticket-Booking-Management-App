// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ANNOTATION
@Controller // An ANNOTATION that tells Spring Boot that a class handles web requests and returns views (like HTML pages).
public class HomeController {
    // Handle GET request for /home
    @GetMapping("/home")
    public String homePage() {
        return "Home"; // Return the Home view
    }
}