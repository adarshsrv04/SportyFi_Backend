package com.sportyfi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyfi.dao.VenueBookingDao;
import com.sportyfi.entity.Bookings;

@Service
public class VenueBookingService {
	
	@Autowired
	VenueBookingDao bookingDao;
	
	public List<Bookings> findAllBookings(UUID userId) {
		return bookingDao.findAllBookings(userId);
	}

	public Bookings saveBooking(Bookings bookings) {
		// TODO Auto-generated method stub
		return bookingDao.saveBooking(bookings);
	}

	public Optional<Bookings> findBookingById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
