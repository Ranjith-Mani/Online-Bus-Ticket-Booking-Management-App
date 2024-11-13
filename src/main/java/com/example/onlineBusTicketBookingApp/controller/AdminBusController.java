// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Bus;
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.service.AdminBusService;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ANNOTATIONS
@Controller // An ANNOTATION that tells Spring Boot that a class handles web requests and returns views (like HTML pages).
@RequestMapping("/admin-buses") // An ANNOTATION in Spring Boot maps HTTP requests to specific methods in a controller, based on the URL.
public class AdminBusController {

    private final AdminBusService busService;
    private final AdminService adminService;
    private final PassengerService passengerService;

    // Constructor injection for services
    public AdminBusController(AdminBusService busService, AdminService adminService, PassengerService passengerService) {
        this.busService = busService;
        this.adminService = adminService;
        this.passengerService = passengerService;
    }

    @Operation(summary = "Get all buses", description = "Fetches a list of all buses.")
    @GetMapping //An ANNOTATION that is used for GETTING response
    public String getAllBuses(Model model) {
        List<Bus> buses = busService.getAllBuses();
        model.addAttribute("buses", buses);
        return "admin-bus-list"; // Thymeleaf template for listing buses
    }

    @Operation(summary = "Get bus details", description = "Fetches details of a specific bus by its ID.")
    @GetMapping("/details/{id}")
    public String getBusDetails(@PathVariable("id") long id, Model model) {
        Bus bus = busService.getBusById(id);
        if (bus != null) {
            model.addAttribute("bus", bus);
            return "admin-bus-details"; // Thymeleaf template for bus details
        } else {
            model.addAttribute("error", "Bus not found.");
            return "error";
        }
    }

    @Operation(summary = "Show add bus form", description = "Displays the form to add a new bus.")
    @GetMapping("/add")
    public String addBusForm(Model model) {
        List<Admin> admins = adminService.getAllAdmins();
        List<Passenger> passengers = passengerService.getAllPassengers();

        model.addAttribute("bus", new Bus());
        model.addAttribute("admins", admins);
        model.addAttribute("passengers", passengers);

        // Check if there are no admins or passengers
        if (admins.isEmpty() || passengers.isEmpty()) {
            model.addAttribute("error", "No admins or passengers available to assign a bus.");
            return "error";
        }

        return "admin-bus-form"; // Thymeleaf template for adding a bus
    }

    @Operation(summary = "Add new bus", description = "Handles adding a new bus to the system.")
    @PostMapping("/add") // An ANNOTATION that is responsible for POSTING http request
    public String addBus(@ModelAttribute Bus bus, Model model) {
        if (bus.getAdmin() == null || bus.getPassenger() == null) {
            model.addAttribute("error", "Admin and Passenger must be selected.");
            return "admin-bus-form"; // Re-show the form with an error message
        }

        busService.saveBus(bus);
        return "redirect:/admin-buses"; // Redirect after saving
    }

    @Operation(summary = "Show edit bus form", description = "Displays the form to edit an existing bus by its ID.")
    @GetMapping("/edit/{id}")
    public String editBusForm(@PathVariable("id") long id, Model model) {
        Bus bus = busService.getBusById(id);
        if (bus != null) {
            List<Admin> admins = adminService.getAllAdmins();
            List<Passenger> passengers = passengerService.getAllPassengers();

            model.addAttribute("bus", bus);
            model.addAttribute("admins", admins);
            model.addAttribute("passengers", passengers);
            return "admin-bus-update-form"; // Thymeleaf template for editing bus
        } else {
            model.addAttribute("error", "Bus not found.");
            return "error";
        }
    }

    @Operation(summary = "Edit bus details", description = "Handles the update of bus details by its ID.")
    @PostMapping("/edit/{id}")
    public String editBus(@PathVariable("id") long id, @ModelAttribute Bus updatedBus, Model model) {
        if (updatedBus.getAdmin() == null || updatedBus.getPassenger() == null) {
            model.addAttribute("error", "Admin and Passenger must be selected.");
            return "admin-bus-update-form"; // Re-show the form with an error message
        }

        Bus updated = busService.updateBus(id, updatedBus);
        if (updated != null) {
            return "redirect:/admin-buses"; // Redirect after updating
        } else {
            model.addAttribute("error", "Bus not found for update.");
            return "error";
        }
    }

    @Operation(summary = "Delete bus", description = "Handles the deletion of a bus by its ID.")
    @PostMapping("/delete/{id}")
    public String deleteBus(@PathVariable("id") long id) {
        boolean deleted = busService.deleteBusById(id);
        if (deleted) {
            return "redirect:/admin-buses"; // Redirect after deletion
        } else {
            return "error"; // Return error page if deletion fails
        }
    }
}
