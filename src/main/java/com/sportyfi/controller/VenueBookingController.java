package com.sportyfi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportyfi.entity.Bookings;
import com.sportyfi.services.SportyfiService;
import com.sportyfi.services.VenueBookingService;

@RestController
@RequestMapping("/sportyfi")
public class VenueBookingController {
	
	@Autowired
	private VenueBookingService venueBookingService;
	
	@GetMapping("/bookings/{userId}")
    public List<Bookings> getAllBookings(@PathVariable UUID userId) {
        return venueBookingService.findAllBookings(userId);
    }

    @GetMapping("/booking/{id}")
    public Optional<Bookings> getBookingById(@PathVariable UUID id) {
        return venueBookingService.findBookingById(id);
    }

    @PostMapping("/createBooking")
    public Bookings createBooking(@RequestBody Bookings booking) {
        return venueBookingService.saveBooking(booking);
    }

}
