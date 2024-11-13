// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// ANNOTATIONS
@Controller // An ANNOTATION that tells Spring Boot that a class handles web requests and returns views (like HTML pages).
@RequestMapping("/passengers") // An ANNOTATION in Spring Boot maps HTTP requests to specific methods in a controller, based on the URL.
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    @Operation(summary = "Get all passengers", description = "Fetches the list of all passengers.")
    public String getAllPassengers(Model model) {
        List<Passenger> result = passengerService.getAllPassengers();
        model.addAttribute("passengers", result);
        return "passengers-list"; // Thymeleaf template for listing passengers
    }

    @GetMapping("/add")
    @Operation(summary = "Load page to add new passenger", description = "Loads the form to add a new passenger.")
    public String loadAddPassengerPage(Model model) {
        Passenger passenger = new Passenger();
        model.addAttribute("passenger", passenger);
        return "add-passenger";
    }

    @PostMapping("/save")
    @Operation(summary = "Save new passenger", description = "Saves the details of a new passenger.")
    public String savePassenger(@ModelAttribute("passenger") Passenger passenger) {
        passengerService.savePassenger(passenger);
        return "redirect:/passengers";
    }

    @GetMapping("/update/{id}")
    @Operation(summary = "Load update form for passenger", description = "Loads the update form for an existing passenger by ID.")
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Optional<Passenger> passengerOptional = Optional.ofNullable(passengerService.getPassengerById(id));
        if (passengerOptional.isEmpty()) {
            return "error"; // Passenger not found, return error view
        }
        model.addAttribute("passenger", passengerOptional.get());
        return "update-passenger";
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "Update passenger details", description = "Updates the details of an existing passenger.")
    public String updatePassengerInfo(@ModelAttribute("passenger") Passenger passenger, @PathVariable("id") long id) {
        passenger.setId(id);
        passengerService.updatePassenger(id, passenger);
        return "redirect:/passengers";
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete passenger", description = "Deletes a passenger by their ID.")
    public String deletePassenger(@PathVariable("id") long id) {
        passengerService.deletePassengerById(id);
        return "redirect:/passengers";
    }

    @GetMapping("/details/{id}")
    @Operation(summary = "Get passenger details", description = "Fetches the details of a specific passenger by ID.")
    public String getPassengerDetails(@PathVariable("id") Long id, Model model) {
        Optional<Passenger> passenger = Optional.ofNullable(passengerService.getPassengerById(id));
        if (passenger.isPresent()) {
            model.addAttribute("passenger", passenger.get());
            return "passenger-details";
        } else {
            model.addAttribute("error", "Passenger not found");
            return "error";
        }
    }

}
