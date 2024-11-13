// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.service.PassengerBookingService;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import com.example.onlineBusTicketBookingApp.exception.BookingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PassengerBookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PassengerBookingService passengerBookingService;

    @Mock
    private AdminService adminService;

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerBookingController passengerBookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passengerBookingController).build();
    }

    @Test
    void testGetAllBookings() throws Exception {
        // Sample list of bookings to be returned by the service
        List<Booking> bookings = Arrays.asList(
                new Booking(1L, "Booking 1", 1L, 1L),
                new Booking(2L, "Booking 2", 1L, 1L));

        when(passengerBookingService.getAllBookings()).thenReturn(bookings);

        // Perform GET request to fetch all bookings
        mockMvc.perform(get("/passenger-bookings"))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-bookings-list"))
                .andExpect(model().attributeExists("bookings"));

        verify(passengerBookingService, times(1)).getAllBookings();
    }

    @Test
    void testShowAddBookingForm() throws Exception {
        // Mock service calls for admins and passengers
        when(adminService.getAllAdmins()).thenReturn(Arrays.asList());
        when(passengerService.getAllPassengers()).thenReturn(Arrays.asList());

        // Perform GET request to show the add booking form
        mockMvc.perform(get("/passenger-bookings/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-add-booking"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("passengers"));

        verify(adminService, times(1)).getAllAdmins();
        verify(passengerService, times(1)).getAllPassengers();
    }

    @Test
    void testSaveBooking() throws Exception {
        // Sample booking to be saved
        Booking booking = new Booking(1L, "Booking 1", 1L, 1L);

        // Perform POST request to save the booking
        mockMvc.perform(post("/passenger-bookings/save")
                        .flashAttr("booking", booking))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passenger-bookings"));

        verify(passengerBookingService, times(1)).saveBooking(any(Booking.class));
    }

    @Test
    void testLoadUpdateForm() throws Exception {
        // Sample booking to be updated
        Booking booking = new Booking(1L, "Booking 1", 1L, 1L);

        when(passengerBookingService.getBookingById(1L)).thenReturn(java.util.Optional.of(booking));
        when(adminService.getAllAdmins()).thenReturn(Arrays.asList());
        when(passengerService.getAllPassengers()).thenReturn(Arrays.asList());

        // Perform GET request to load the update form
        mockMvc.perform(get("/passenger-bookings/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-update-booking"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("passengers"));

        verify(passengerBookingService, times(1)).getBookingById(1L);
        verify(adminService, times(1)).getAllAdmins();
        verify(passengerService, times(1)).getAllPassengers();
    }

    @Test
    void testUpdateBookingInfo() throws Exception {
        // Sample updated booking
        Booking booking = new Booking(1L, "Updated Booking", 1L, 1L);

        // Perform POST request to update the booking
        mockMvc.perform(post("/passenger-bookings/update/1")
                        .flashAttr("booking", booking))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passenger-bookings"));

        verify(passengerBookingService, times(1)).updateBooking(eq(1L), any(Booking.class));
    }

    @Test
    void testDeleteBookingById() throws Exception {
        // Mock the deletion of a booking
        when(passengerBookingService.deleteBookingById(1L)).thenReturn(true);

        // Perform POST request to delete the booking
        mockMvc.perform(post("/passenger-bookings/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passenger-bookings"));

        verify(passengerBookingService, times(1)).deleteBookingById(1L);
    }

    @Test
    void testGetBookingDetails() throws Exception {
        // Sample booking for details
        Booking booking = new Booking(1L, "Booking 1", 1L, 1L);

        when(passengerBookingService.getBookingById(1L)).thenReturn(java.util.Optional.of(booking));

        // Perform GET request to get booking details
        mockMvc.perform(get("/passenger-bookings/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-booking-details"))
                .andExpect(model().attributeExists("booking"));

        verify(passengerBookingService, times(1)).getBookingById(1L);
    }

    @Test
    void testGetBookingDetailsNotFound() throws Exception {
        // Mock the service call to return empty (not found)
        when(passengerBookingService.getBookingById(1L)).thenReturn(java.util.Optional.empty());

        // Perform GET request to get booking details when not found
        mockMvc.perform(get("/passenger-bookings/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        verify(passengerBookingService, times(1)).getBookingById(1L);
    }
}
