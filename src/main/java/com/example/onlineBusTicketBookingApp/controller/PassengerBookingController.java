// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.service.PassengerBookingService;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import com.example.onlineBusTicketBookingApp.exception.BookingNotFoundException;
import io.swagger.v3.oas.annotations.Operation; // Import the @Operation annotation
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ANNOTATIONS
@Controller
@RequestMapping("/passenger-bookings")
public class PassengerBookingController {

    private final PassengerBookingService passengerBookingService;
    private final AdminService adminService;
    private final PassengerService passengerService;

    public PassengerBookingController(PassengerBookingService passengerBookingService, AdminService adminService, PassengerService passengerService) {
        this.passengerBookingService = passengerBookingService;
        this.adminService = adminService;
        this.passengerService = passengerService;
    }

    @GetMapping
    @Operation(summary = "Get all passenger bookings", description = "Fetch a list of all bookings made by passengers.")
    public String getAllBookings(Model model) {
        List<Booking> bookings = passengerBookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "passenger-bookings-list";
    }

    @GetMapping("/add")
    @Operation(summary = "Show add booking form", description = "Display the form for adding a new booking, including admins and passengers data.")
    public String showAddBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("passengers", passengerService.getAllPassengers());
        return "passenger-add-booking";
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new booking", description = "Save a newly created booking to the system.")
    public String saveBooking(@ModelAttribute("booking") @Valid Booking booking, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("admins", adminService.getAllAdmins());
            model.addAttribute("passengers", passengerService.getAllPassengers());
            return "passenger-add-booking";
        }
        passengerBookingService.saveBooking(booking);
        return "redirect:/passenger-bookings";
    }

    @GetMapping("/update/{id}")
    @Operation(summary = "Load update booking form", description = "Load the form to update an existing booking by its ID.")
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Booking booking = passengerBookingService.getBookingById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + id + " not found!"));

        model.addAttribute("booking", booking);
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("passengers", passengerService.getAllPassengers());
        return "passenger-update-booking";
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "Update booking info", description = "Update the information of an existing booking by its ID.")
    public String updateBookingInfo(@ModelAttribute("booking") @Valid Booking booking, BindingResult result,
                                    @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("admins", adminService.getAllAdmins());
            model.addAttribute("passengers", passengerService.getAllPassengers());
            return "passenger-update-booking";
        }
        booking.setId(id);
        passengerBookingService.updateBooking(id, booking);
        return "redirect:/passenger-bookings";
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete booking", description = "Delete a booking from the system by its ID.")
    public String deleteBookingById(@PathVariable("id") long id) {
        boolean deleted = passengerBookingService.deleteBookingById(id);
        if (deleted) {
            return "redirect:/passenger-bookings"; // Redirect to bookings list after deletion
        } else {
            return "error"; // Error page if the booking was not found or not deleted
        }
    }

    @GetMapping("/details/{id}")
    @Operation(summary = "Get booking details", description = "Fetch detailed information about a booking by its ID.")
    public String getBookingDetails(@PathVariable("id") Long id, Model model) {
        Booking booking = passengerBookingService.getBookingById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + id + " not found!"));

        model.addAttribute("booking", booking);
        return "passenger-booking-details";
    }
}
