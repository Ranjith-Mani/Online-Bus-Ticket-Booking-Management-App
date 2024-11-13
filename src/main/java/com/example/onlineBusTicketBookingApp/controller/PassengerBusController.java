// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.entity.Bus;
import com.example.onlineBusTicketBookingApp.service.PassengerBusService;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ANNOTATIONS
@Controller // An ANNOTATION that tells Spring Boot that a class handles web requests and returns views (like HTML pages).
@RequestMapping("/passenger-buses") // An ANNOTATION in Spring Boot maps HTTP requests to specific methods in a controller, based on the URL.
public class PassengerBusController {

    private final PassengerBusService passengerBusService;
    private final AdminService adminService;
    private final PassengerService passengerService;

    public PassengerBusController(PassengerBusService passengerBusService, AdminService adminService, PassengerService passengerService) {
        this.passengerBusService = passengerBusService;
        this.adminService = adminService;
        this.passengerService = passengerService;
    }

    @Operation(summary = "Retrieve all buses", description = "Fetches a list of all buses available.")
    @GetMapping
    public String getAllBuses(Model model) {
        List<Bus> buses = passengerBusService.getAllBuses();
        model.addAttribute("buses", buses);
        return "passenger-bus-list";
    }

    @Operation(summary = "Retrieve buses for a specific passenger", description = "Fetches buses assigned to a particular passenger based on their ID.")
    @GetMapping("/passenger/{passengerId}")
    public String getBusesByPassenger(@PathVariable("passengerId") long passengerId, Model model) {
        Passenger passenger = passengerService.getPassengerById(passengerId);
        if (passenger == null) {
            model.addAttribute("error", "Passenger not found.");
            return "error"; // Custom error template
        }
        List<Bus> buses = passengerBusService.getBusesByPassenger(passenger);
        model.addAttribute("buses", buses);
        return "passenger-bus-list";
    }

    @Operation(summary = "Retrieve buses for a specific admin", description = "Fetches buses managed by a particular admin based on their ID.")
    @GetMapping("/admin/{adminId}")
    public String getBusesByAdmin(@PathVariable("adminId") long adminId, Model model) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin == null) {
            model.addAttribute("error", "Admin not found.");
            return "error"; // Custom error template
        }
        List<Bus> buses = passengerBusService.getBusesByAdmin(admin);
        model.addAttribute("buses", buses);
        return "passenger-bus-list";
    }

    @Operation(summary = "Retrieve details of a specific bus", description = "Fetches details of a bus based on the given bus ID.")
    @GetMapping("/details/{id}")
    public String getBusDetails(@PathVariable("id") long id, Model model) {
        Bus bus = passengerBusService.getBusById(id);
        if (bus != null) {
            model.addAttribute("bus", bus);
            return "passenger-bus-details";
        } else {
            model.addAttribute("error", "Bus not found.");
            return "error";
        }
    }

    @Operation(summary = "Add a new bus", description = "Displays a form to add a new bus with available admins and passengers.")
    @GetMapping("/add")
    public String addBusForm(Model model) {
        List<Admin> admins = adminService.getAllAdmins();
        List<Passenger> passengers = passengerService.getAllPassengers();

        model.addAttribute("bus", new Bus());
        model.addAttribute("admins", admins);
        model.addAttribute("passengers", passengers);

        // Check if there are no admins or passengers
        if (admins.isEmpty() || passengers.isEmpty()) {
            model.addAttribute("error", "No admins or passengers available to assign bus.");
            return "error";
        }

        return "passenger-bus-form";
    }

    @Operation(summary = "Submit a new bus", description = "Handles the submission of a new bus to the system.")
    @PostMapping("/add")
    public String addBus(@ModelAttribute Bus bus, Model model) {
        if (bus.getAdmin() == null || bus.getPassenger() == null) {
            model.addAttribute("error", "Admin and Passenger must be selected.");
            return "passenger-bus-form"; // Re-show the form with an error message
        }

        passengerBusService.saveBus(bus);
        return "redirect:/passenger-buses";
    }

    @Operation(summary = "Edit an existing bus", description = "Displays a form to edit an existing bus based on the bus ID.")
    @GetMapping("/edit/{id}")
    public String editBusForm(@PathVariable("id") long id, Model model) {
        Bus bus = passengerBusService.getBusById(id);
        if (bus != null) {
            List<Admin> admins = adminService.getAllAdmins();
            List<Passenger> passengers = passengerService.getAllPassengers();

            model.addAttribute("bus", bus);
            model.addAttribute("admins", admins);
            model.addAttribute("passengers", passengers);
            return "passenger-bus-update-form";
        } else {
            model.addAttribute("error", "Bus not found.");
            return "error";
        }
    }

    @Operation(summary = "Submit changes to a bus", description = "Handles the submission of updated bus details.")
    @PostMapping("/edit/{id}")
    public String editBus(@PathVariable("id") long id, @ModelAttribute Bus updatedBus, Model model) {
        if (updatedBus.getAdmin() == null || updatedBus.getPassenger() == null) {
            model.addAttribute("error", "Admin and Passenger must be selected.");
            return "passenger-bus-update-form";
        }

        Bus updated = passengerBusService.updateBus(id, updatedBus);
        if (updated != null) {
            return "redirect:/passenger-buses";
        } else {
            model.addAttribute("error", "Bus not found for update.");
            return "error";
        }
    }

    @Operation(summary = "Delete a bus", description = "Deletes a bus based on the given bus ID.")
    @PostMapping("/delete/{id}")
    public String deleteBus(@PathVariable("id") long id) {
        boolean deleted = passengerBusService.deleteBusById(id);
        if (deleted) {
            return "redirect:/passenger-buses";
        } else {
            return "error";
        }
    }
}
