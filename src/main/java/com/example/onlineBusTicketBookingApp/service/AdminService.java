// PACKAGE
package com.example.onlineBusTicketBookingApp.service;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;

import java.util.List;

public interface AdminService {

    List<Admin> getAllAdmins();

    Admin getAdminById(long id);

    Admin saveAdmin(Admin admin);

    Admin updateAdmin(Long id, Admin updatedAdmin);

    boolean deleteAdminById(long id);
}