package com.sportyfi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sportyfi.entity.Bookings;
import com.sportyfi.entity.Matches;

public interface SportyfiService {

	List<Bookings> findAllBookings();

	Bookings saveBooking(Bookings bookings);

	Optional<Bookings> findBookingById(UUID id);

	List<Matches> findAllMatches(String sport);

	Matches findMatchById(UUID matchId);

	boolean saveMatch(Matches matches);

	boolean updateMatchField(UUID matchId, String field, String newValue);

	List<Matches> findMatchesByUserId(UUID userId);

	List<Matches> findMatchesByCity(String city);

}
