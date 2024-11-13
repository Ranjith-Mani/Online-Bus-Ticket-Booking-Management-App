// PACKAGE
package com.example.onlineBusTicketBookingApp.service.impl;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.repository.AdminRepository;
import com.example.onlineBusTicketBookingApp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service implementation for AdminService interface
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    // Constructor-based dependency injection
    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll(); // Retrieves all admin records from the database
    }

    @Override
    public Admin getAdminById(long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.orElse(null); // Retrieves an admin by ID or returns null if not found
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Email already exists."); // Throws exception if email is already taken
        }
        adminRepository.save(admin); // Saves a new admin record
        return admin;
    }


    @Override
    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        Optional<Admin> existingAdminOpt = adminRepository.findById(id);
        if (existingAdminOpt.isPresent()) {
            Admin existingAdmin = existingAdminOpt.get();
            // Update fields of existing admin with the new values
            existingAdmin.setName(updatedAdmin.getName());
            existingAdmin.setEmail(updatedAdmin.getEmail());
            existingAdmin.setPhoneNumber(updatedAdmin.getPhoneNumber());
            existingAdmin.setAddress(updatedAdmin.getAddress());

            return adminRepository.save(existingAdmin); // Save updated admin record
        }
        return null; // Return null if the admin was not found
    }

    @Override
    public boolean deleteAdminById(long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id); // Deletes admin by ID
            return true;
        }
        return false; // Return false if the admin was not found
    }
}