package com.sportyfi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.sportyfi.dao.VenueBookingDao;
import com.sportyfi.entity.Bookings;

public class VenueBookingService {
	
	@Autowired
	VenueBookingDao bookingDao;
	
	public List<Bookings> findAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	public Bookings saveBooking(Bookings bookings) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Bookings> findBookingById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
