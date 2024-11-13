// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PassengerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerController passengerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController).build();
    }

    @Test
    void testGetAllPassengers() throws Exception {
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger(1L, "John", "Doe", "john@example.com", "1234567890", "123 Main St"));
        passengers.add(new Passenger(2L, "Jane", "Smith", "jane@example.com", "0987654321", "456 Elm St"));

        when(passengerService.getAllPassengers()).thenReturn(passengers);

        mockMvc.perform(get("/passengers"))
                .andExpect(status().isOk())
                .andExpect(view().name("passengers-list"))
                .andExpect(model().attributeExists("passengers"));

        verify(passengerService, times(1)).getAllPassengers();
    }

    @Test
    void testLoadAddPassengerPage() throws Exception {
        mockMvc.perform(get("/passengers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-passenger"))
                .andExpect(model().attributeExists("passenger"));
    }

    @Test
    void testSavePassenger() throws Exception {
        Passenger passenger = new Passenger(1L, "John", "Doe", "john@example.com", "1234567890", "123 Main St");

        mockMvc.perform(post("/passengers/save")
                        .flashAttr("passenger", passenger))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passengers"));

        verify(passengerService, times(1)).savePassenger(any(Passenger.class));
    }

    @Test
    void testLoadUpdateForm() throws Exception {
        Passenger passenger = new Passenger(1L, "John", "Doe", "john@example.com", "1234567890", "123 Main St");

        when(passengerService.getPassengerById(1L)).thenReturn(passenger);

        mockMvc.perform(get("/passengers/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update-passenger"))
                .andExpect(model().attributeExists("passenger"));

        verify(passengerService, times(1)).getPassengerById(1L);
    }

    @Test
    void testUpdatePassengerInfo() throws Exception {
        Passenger passenger = new Passenger(1L, "John", "Doe", "john@example.com", "1234567890", "123 Main St");

        mockMvc.perform(post("/passengers/update/1")
                        .flashAttr("passenger", passenger))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passengers"));

        verify(passengerService, times(1)).updatePassenger(eq(1L), any(Passenger.class));
    }

    @Test
    void testDeletePassenger() throws Exception {
        mockMvc.perform(post("/passengers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passengers"));

        verify(passengerService, times(1)).deletePassengerById(1L);
    }

    @Test
    void testGetPassengerDetails() throws Exception {
        Passenger passenger = new Passenger(1L, "John", "Doe", "john@example.com", "1234567890", "123 Main St");

        when(passengerService.getPassengerById(1L)).thenReturn(passenger);

        mockMvc.perform(get("/passengers/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-details"))
                .andExpect(model().attributeExists("passenger"));

        verify(passengerService, times(1)).getPassengerById(1L);
    }

    @Test
    void testGetPassengerDetailsNotFound() throws Exception {
        when(passengerService.getPassengerById(1L)).thenReturn(null);

        mockMvc.perform(get("/passengers/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        verify(passengerService, times(1)).getPassengerById(1L);
    }
}