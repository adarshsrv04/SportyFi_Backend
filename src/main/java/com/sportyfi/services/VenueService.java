package com.sportyfi.services;

import com.sportyfi.dao.VenueDao;
import com.sportyfi.dao.VenueRequestDao;
import com.sportyfi.dao.VenueImageDao;
import com.sportyfi.entity.Venues;
import com.sportyfi.entity.VenueRequest;
//import com.sportyfi.entity.VenueImage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class VenueService {

	private final VenueDao venueDao;
	private final VenueRequestDao venueRequestDao;
//    private final VenueImageDao venueImageDao;

	public VenueService(VenueDao venueDao, VenueRequestDao venueRequestDao, VenueImageDao venueImageDao) {
		this.venueDao = venueDao;
		this.venueRequestDao = venueRequestDao;
//        this.venueImageDao = venueImageDao;
	}

	@Transactional
	public Venues approveVenueRequest(UUID venueRequestId) {
		// Fetch from venue_requests
		VenueRequest venueRequest = venueRequestDao.findById(venueRequestId);
		System.out.println(venueRequest);
		if (venueRequest == null) {
			throw new RuntimeException("Venue Request not found with id: " + venueRequestId);
		}

		// Create Venue entity from VenueRequest
		Venues venue = new Venues();
		venue.setName(venueRequest.getName());
		venue.setDescription(venueRequest.getDescription());
		venue.setLocation(venueRequest.getLocation());
		venue.setPrice_per_hour(venueRequest.getPrice_per_hour());
		venue.setContact_phone(venueRequest.getContact_phone());
		venue.setContact_email(venueRequest.getContact_email());
		venue.setOwner_id(venueRequest.getOwner_id());
		venue.setSports(venueRequest.getSports());
		venue.setAmenities(venueRequest.getAmenities());
		venue.setIs_verified(true);

		Venues savedVenue = venueDao.save(venue);
		System.out.println(savedVenue.toString());
		// Save images to venue_images
//        venueRequest.getImages().forEach(img -> {
//            VenueImage venueImage = new VenueImage();
//            venueImage.setVenueId(savedVenue.getId());
//            venueImage.setImageData(img.getImageData());
//            venueImageDao.save(venueImage);
//        });

		// Delete request after approval
//        venueRequestDao.delete(venueRequest);

		return savedVenue;
	}

	public List<Venues> getAll() {
		return venueDao.findAll();
	}

	public List<Venues> findVenues(Optional<String> sport, Optional<String> location, Optional<Integer> minPrice,
			Optional<Integer> maxPrice, Optional<String> searchQuery) {
		int min = minPrice.orElse(0);
		int max = maxPrice.orElse(5000);

		return venueDao.searchVenues(sport.orElse(null), location.orElse(null), min, max,
				searchQuery.orElse(null));
	}

	public Venues getById(UUID id) {
		return venueDao.findById(id);
	}
}
