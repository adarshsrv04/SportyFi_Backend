package com.sportyfi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sportyfi.entity.Bookings;
import com.sportyfi.services.SportyfiService;

public class VenueBookingController {
	
	@Autowired
	private SportyfiService sportyfiService;
	
	@GetMapping("/bookings")
    public List<Bookings> getAllBookings() {
        return sportyfiService.findAllBookings();
    }

    @GetMapping("/booking/{id}")
    public Optional<Bookings> getBookingById(@PathVariable UUID id) {
        return sportyfiService.findBookingById(id);
    }

    @PostMapping("/createBooking")
    public Bookings createBooking(@RequestBody Bookings booking) {
        return sportyfiService.saveBooking(booking);
    }

}
