// PACKAGE
package com.example.onlineBusTicketBookingApp.controller;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import io.swagger.v3.oas.annotations.Operation; // Import the @Operation annotation
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// ANNOTATIONS
@Controller
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @Operation(summary = "Get all admins", description = "Fetch a list of all admins in the system.")
    public String getAllAdmins(Model model) {
        List<Admin> result = adminService.getAllAdmins();
        model.addAttribute("admins", result);
        return "admins-list"; // Thymeleaf template for listing admins
    }

    @GetMapping("/add")
    @Operation(summary = "Load add admin page", description = "Load the page for adding a new admin.")
    public String loadAddAdminPage(Model model) {
        Admin admin = new Admin();
        model.addAttribute("admin", admin);
        return "add-admin";
    }

    @PostMapping("/save")
    @Operation(summary = "Save new admin", description = "Save a newly added admin to the system.")
    public String saveAdmin(@ModelAttribute("admin") Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admins";
    }

    @GetMapping("/update/{id}")
    @Operation(summary = "Load update admin form", description = "Load the form to update an admin's information.")
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Admin admin = adminService.getAdminById(id);
        if (admin == null) {
            return "error";
        }
        model.addAttribute("admin", admin);
        return "update-admin";
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "Update admin info", description = "Update the information of an existing admin.")
    public String updateAdminInfo(@ModelAttribute("admin") Admin admin, @PathVariable("id") long id) {
        admin.setId(id);
        adminService.updateAdmin(id, admin);
        return "redirect:/admins";
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete admin", description = "Delete an admin from the system by their ID.")
    public String deleteAdmin(@PathVariable("id") long id) {
        adminService.deleteAdminById(id);
        return "redirect:/admins";
    }

    @GetMapping("/details/{id}")
    @Operation(summary = "Get admin details", description = "Fetch detailed information about an admin by their ID.")
    public String getAdminDetails(@PathVariable Long id, Model model) {
        Optional<Admin> admin = Optional.ofNullable(adminService.getAdminById(id));
        if (admin.isPresent()) {
            model.addAttribute("admin", admin.get());
            return "admin-details";
        } else {
            return "error"; // Return error page when not found
        }
    }
}
