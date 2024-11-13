// PACKAGE
package com.example.onlineBusTicketBookingApp.service;

// IMPORTS
import com.example.onlineBusTicketBookingApp.entity.Admin;
import com.example.onlineBusTicketBookingApp.entity.Bus;
import com.example.onlineBusTicketBookingApp.entity.Passenger;

import java.util.List;

// Interface for AdminBusService, which defines bus-related operations
public interface AdminBusService {

    List<Bus> getAllBuses();

    boolean deleteBusById(long id);

    Bus saveBus(Bus bus);

    Bus updateBus(long id, Bus updatedBus);

    Bus getBusById(long id);

    List<Bus> getBusesByPassenger(Passenger passenger);

    List<Bus> getBusesByAdmin(Admin admin);
}
