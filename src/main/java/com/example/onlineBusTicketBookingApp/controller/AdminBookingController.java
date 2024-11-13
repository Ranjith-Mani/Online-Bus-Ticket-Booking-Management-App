// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.service.AdminBookingService;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

// ANNOTATIONS
@Controller // An ANNOTATION that tells Spring Boot that a class handles web requests and returns views (like HTML pages).
@RequestMapping("/admin-bookings") // An ANNOTATION in Spring Boot maps HTTP requests to specific methods in a controller, based on the URL.
public class AdminBookingController {

    private final AdminBookingService adminBookingService;
    private final AdminService adminService;
    private final PassengerService passengerService;

    public AdminBookingController(AdminBookingService adminBookingService, AdminService adminService, PassengerService passengerService) {
        this.adminBookingService = adminBookingService;
        this.adminService = adminService;
        this.passengerService = passengerService;
    }

    @Operation(summary = "Get all bookings", description = "Fetch and display all bookings")
    @GetMapping //An ANNOTATION that is used for GETTING response
    public String getAllBookings(Model model) {
        List<Booking> bookings = adminBookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin-bookings-list";
    }

    @Operation(summary = "Show add booking form", description = "Display a form to add a new booking")
    @GetMapping("/add")
    public String showAddBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("passengers", passengerService.getAllPassengers());
        return "admin-add-booking";
    }

    @Operation(summary = "Save new booking", description = "Save the new booking details to the database")
    @PostMapping("/save") // An ANNOTATION that is responsible for POSTING http request
    public String saveBooking(@ModelAttribute("booking") @Valid Booking booking, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("admins", adminService.getAllAdmins());
            model.addAttribute("passengers", passengerService.getAllPassengers());
            return "admin-add-booking";
        }
        adminBookingService.saveBooking(booking);
        return "redirect:/admin-bookings";
    }

    @Operation(summary = "Load update booking form", description = "Load the booking data for updating")
    @GetMapping("/update/{id}")
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Optional<Booking> bookingOpt = Optional.ofNullable(adminBookingService.getBookingById(id));
        if (bookingOpt.isEmpty()) {
            return "error";
        }
        Booking booking = bookingOpt.get();
        model.addAttribute("booking", booking);
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("patients", passengerService.getAllPassengers());
        return "admin-update-booking";
    }

    @Operation(summary = "Update booking info", description = "Update booking details based on booking ID")
    @PostMapping("/update/{id}")
    public String updateBookingInfo(@ModelAttribute("booking") @Valid Booking booking, BindingResult result, @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("admins", adminService.getAllAdmins());
            model.addAttribute("passengers", passengerService.getAllPassengers());
            return "admin-update-booking";
        }
        booking.setId(id);
        adminBookingService.updateBooking(id, booking);
        return "redirect:/admin-bookings";
    }

    @Operation(summary = "Delete booking by ID", description = "Delete a booking record based on booking ID")
    @PostMapping("/delete/{id}")
    public String deleteBookingById(@PathVariable("id") long id) {
        boolean deleted = adminBookingService.deleteBookingById(id);
        if (deleted) {
            return "redirect:/admin-bookings"; // Update to match GET mapping
        } else {
            return "error";
        }
    }

    @Operation(summary = "Get booking details", description = "View detailed information about a specific booking")
    @GetMapping("/details/{id}")
    public String getBookingDetails(@PathVariable("id") Long id, Model model) {
        Optional<Booking> booking = Optional.ofNullable(adminBookingService.getBookingById(id));
        if (booking.isPresent()) {
            model.addAttribute("booking", booking.get());
            return "admin-booking-details";
        } else {
            model.addAttribute("errorMessage", "Bookings not found!");
            return "error";
        }
    }
}
