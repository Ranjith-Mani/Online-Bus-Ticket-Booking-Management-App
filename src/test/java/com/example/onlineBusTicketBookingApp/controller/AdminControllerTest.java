// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

//IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.service.AdminService;
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

class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testGetAllAdmins() throws Exception {
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin(1L, "John", "Doe", "john@example.com"));
        admins.add(new Admin(2L, "Jane", "Smith", "jane@example.com"));

        when(adminService.getAllAdmins()).thenReturn(admins);

        mockMvc.perform(get("/admins"))
                .andExpect(status().isOk())
                .andExpect(view().name("admins-list"))
                .andExpect(model().attributeExists("admins"));

        verify(adminService, times(1)).getAllAdmins();
    }

    @Test
    void testLoadAddAdminPage() throws Exception {
        mockMvc.perform(get("/admins/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-admin"))
                .andExpect(model().attributeExists("admin"));
    }

    @Test
    void testSaveAdmin() throws Exception {
        Admin admin = new Admin(1L, "John", "Doe", "john@example.com");

        mockMvc.perform(post("/admins/save")
                        .flashAttr("admin", admin))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admins"));

        verify(adminService, times(1)).saveAdmin(any(Admin.class));
    }

    @Test
    void testLoadUpdateForm() throws Exception {
        Admin admin = new Admin(1L, "John", "Doe", "john@example.com");

        when(adminService.getAdminById(1L)).thenReturn(admin);

        mockMvc.perform(get("/admins/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update-admin"))
                .andExpect(model().attributeExists("admin"));

        verify(adminService, times(1)).getAdminById(1L);
    }

    @Test
    void testUpdateAdminInfo() throws Exception {
        Admin admin = new Admin(1L, "John", "Doe", "john@example.com");

        mockMvc.perform(post("/admins/update/1")
                        .flashAttr("admin", admin))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admins"));

        verify(adminService, times(1)).updateAdmin(eq(1L), any(Admin.class));
    }

    @Test
    void testDeleteAdmin() throws Exception {
        mockMvc.perform(post("/admins/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admins"));

        verify(adminService, times(1)).deleteAdminById(1L);
    }

    @Test
    void testGetAdminDetails() throws Exception {
        Admin admin = new Admin(1L, "John", "Doe", "john@example.com");

        when(adminService.getAdminById(1L)).thenReturn(admin);

        mockMvc.perform(get("/admins/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-details"))
                .andExpect(model().attributeExists("admin"));

        verify(adminService, times(1)).getAdminById(1L);
    }

    @Test
    void testGetAdminDetailsNotFound() throws Exception {
        when(adminService.getAdminById(1L)).thenReturn(null);

        mockMvc.perform(get("/admins/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        verify(adminService, times(1)).getAdminById(1L);
    }
}