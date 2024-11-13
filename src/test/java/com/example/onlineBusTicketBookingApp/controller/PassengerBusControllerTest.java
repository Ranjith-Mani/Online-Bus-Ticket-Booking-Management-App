// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Passenger;
import com.example.onlineBusTicketBookingApp.entity.Bus;
import com.example.onlineBusTicketBookingApp.service.PassengerBusService;
import com.example.onlineBusTicketBookingApp.service.PassengerService;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PassengerBusControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PassengerBusService passengerBusService;

    @Mock
    private AdminService adminService;

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerBusController passengerBusController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passengerBusController).build();
    }

    @Test
    void testGetAllBuses() throws Exception {
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus(1L, "Bus1", "Route1"));
        buses.add(new Bus(2L, "Bus2", "Route2"));

        when(passengerBusService.getAllBuses()).thenReturn(buses);

        mockMvc.perform(get("/passenger-buses"))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-bus-list"))
                .andExpect(model().attributeExists("buses"));

        verify(passengerBusService, times(1)).getAllBuses();
    }

    @Test
    void testGetBusesByPassenger() throws Exception {
        Passenger passenger = new Passenger(1L, "John", "Doe", "john.doe@example.com");
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus(1L, "Bus1", "Route1"));

        when(passengerService.getPassengerById(1L)).thenReturn(passenger);
        when(passengerBusService.getBusesByPassenger(passenger)).thenReturn(buses);

        mockMvc.perform(get("/passenger-buses/passenger/{passengerId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-bus-list"))
                .andExpect(model().attributeExists("buses"))
                .andExpect(model().attributeExists("passenger"));

        verify(passengerService, times(1)).getPassengerById(1L);
        verify(passengerBusService, times(1)).getBusesByPassenger(passenger);
    }

    @Test
    void testGetBusesByAdmin() throws Exception {
        Admin admin = new Admin(1L, "Admin1", "admin1@example.com");
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus(1L, "Bus1", "Route1"));

        when(adminService.getAdminById(1L)).thenReturn(admin);
        when(passengerBusService.getBusesByAdmin(admin)).thenReturn(buses);

        mockMvc.perform(get("/passenger-buses/admin/{adminId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-bus-list"))
                .andExpect(model().attributeExists("buses"))
                .andExpect(model().attributeExists("admin"));

        verify(adminService, times(1)).getAdminById(1L);
        verify(passengerBusService, times(1)).getBusesByAdmin(admin);
    }

    @Test
    void testGetBusDetails() throws Exception {
        Bus bus = new Bus(1L, "Bus1", "Route1");
        when(passengerBusService.getBusById(1L)).thenReturn(bus);

        mockMvc.perform(get("/passenger-buses/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-bus-details"))
                .andExpect(model().attributeExists("bus"));

        verify(passengerBusService, times(1)).getBusById(1L);
    }

    @Test
    void testGetBusDetailsNotFound() throws Exception {
        when(passengerBusService.getBusById(1L)).thenReturn(null);

        mockMvc.perform(get("/passenger-buses/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"));

        verify(passengerBusService, times(1)).getBusById(1L);
    }

    @Test
    void testAddBusForm() throws Exception {
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin(1L, "Admin1", "admin1@example.com"));
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger(1L, "John", "Doe", "john.doe@example.com"));

        when(adminService.getAllAdmins()).thenReturn(admins);
        when(passengerService.getAllPassengers()).thenReturn(passengers);

        mockMvc.perform(get("/passenger-buses/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-bus-form"))
                .andExpect(model().attributeExists("bus"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("passengers"));

        verify(adminService, times(1)).getAllAdmins();
        verify(passengerService, times(1)).getAllPassengers();
    }

    @Test
    void testAddBus() throws Exception {
        Bus bus = new Bus(1L, "Bus1", "Route1");
        when(passengerBusService.saveBus(any(Bus.class))).thenReturn(bus);

        mockMvc.perform(post("/passenger-buses/add")
                        .flashAttr("bus", bus))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passenger-buses"));

        verify(passengerBusService, times(1)).saveBus(any(Bus.class));
    }

    @Test
    void testEditBusForm() throws Exception {
        Bus bus = new Bus(1L, "Bus1", "Route1");
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin(1L, "Admin1", "admin1@example.com"));
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger(1L, "John", "Doe", "john.doe@example.com"));

        when(passengerBusService.getBusById(1L)).thenReturn(bus);
        when(adminService.getAllAdmins()).thenReturn(admins);
        when(passengerService.getAllPassengers()).thenReturn(passengers);

        mockMvc.perform(get("/passenger-buses/edit/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("passenger-bus-update-form"))
                .andExpect(model().attributeExists("bus"))
                .andExpect(model().attributeExists("admins"))
                .andExpect(model().attributeExists("passengers"));

        verify(passengerBusService, times(1)).getBusById(1L);
    }

    @Test
    void testEditBus() throws Exception {
        Bus bus = new Bus(1L, "Bus1", "Route1");
        when(passengerBusService.updateBus(eq(1L), any(Bus.class))).thenReturn(bus);

        mockMvc.perform(post("/passenger-buses/edit/{id}", 1L)
                        .flashAttr("bus", bus))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passenger-buses"));

        verify(passengerBusService, times(1)).updateBus(eq(1L), any(Bus.class));
    }

    @Test
    void testDeleteBus() throws Exception {
        when(passengerBusService.deleteBusById(1L)).thenReturn(true);

        mockMvc.perform(post("/passenger-buses/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passenger-buses"));

        verify(passengerBusService, times(1)).deleteBusById(1L);
    }
}