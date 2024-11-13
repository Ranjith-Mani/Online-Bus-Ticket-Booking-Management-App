// PACKAGE
package com.example.onlineBusTicketBookingApp.exception;

public class NoAdminsOrPassengersException extends RuntimeException {

    // Constructor to pass the exception message to the superclass
    public NoAdminsOrPassengersException(String message) {
        super(message); // Pass the message to the superclass constructor
    }
}