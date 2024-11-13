// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ANNOTATION
@Controller // An ANNOTATION that tells Spring Boot that a class handles web requests and returns views (like HTML pages).
public class AdminHomeController {
    @GetMapping("/admins-home")
    public String adminHomePage() {
        return "Admin-home";
    }
}