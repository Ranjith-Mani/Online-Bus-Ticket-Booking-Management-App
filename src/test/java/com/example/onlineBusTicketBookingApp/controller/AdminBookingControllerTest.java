// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Booking;
import com.example.onlineBusTicketBookingApp.service.AdminBookingService;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminBookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminBookingService adminBookingService;

    @Mock
    private AdminService adminService;

    @Mock
    private PassengerService passengerService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AdminBookingController adminBookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminBookingController).build();
    }

    @Test
    void testGetAllBookings() throws Exception {
        List<Booking> bookings = Collections.singletonList(new Booking());
        when(adminBookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/admin-bookings"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-bookings-list"))
                .andExpect(model().attributeExists("bookings"));

        verify(adminBookingService, times(1)).getAllBookings();
        verify(model, times(1)).addAttribute("bookings", bookings);
    }

    @Test
    void testShowAddBookingForm() throws Exception {
        when(adminService.getAllAdmins()).thenReturn(Collections.emptyList());
        when(passengerService.getAllPassengers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin-bookings/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-booking"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("passengers"));
    }

    @Test
    void testSaveBookingWithValidData() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);

        mockMvc.perform(post("/admin-bookings/save")
                        .flashAttr("booking", new Booking()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-bookings"));

        verify(adminBookingService, times(1)).saveBooking(any(Booking.class));
    }

    @Test
    void testSaveBookingWithErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/admin-bookings/save")
                        .flashAttr("booking", new Booking()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-booking"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("passengers"));
    }

    @Test
    void testLoadUpdateForm() throws Exception {
        Booking booking = new Booking();
        when(adminBookingService.getBookingById(anyLong())).thenReturn(booking);

        mockMvc.perform(get("/admin-bookings/update/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-update-booking"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("patients"));

        verify(adminBookingService, times(1)).getBookingById(1L);
    }

    @Test
    void testUpdateBookingInfoWithValidData() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);

        mockMvc.perform(post("/admin-bookings/update/{id}", 1L)
                        .flashAttr("booking", new Booking()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-bookings"));

        verify(adminBookingService, times(1)).updateBooking(anyLong(), any(Booking.class));
    }

    @Test
    void testUpdateBookingInfoWithErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/admin-bookings/update/{id}", 1L)
                        .flashAttr("booking", new Booking()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-update-booking"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("passengers"));
    }

    @Test
    void testDeleteBooking() throws Exception {
        mockMvc.perform(post("/admin-bookings/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-bookings"));

        verify(adminBookingService, times(1)).deleteBookingById(1L);
    }

    @Test
    void testGetBookingDetails() throws Exception {
        Booking booking = new Booking();
        when(adminBookingService.getBookingById(anyLong())).thenReturn(booking);

        mockMvc.perform(get("/admin-bookings/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-booking-details"))
                .andExpect(model().attributeExists("booking"));

        verify(adminBookingService, times(1)).getBookingById(1L);
    }

    @Test
    void testGetBookingDetails_NotFound() throws Exception {
        when(adminBookingService.getBookingById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/admin-bookings/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}