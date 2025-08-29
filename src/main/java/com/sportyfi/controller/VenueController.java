package com.sportyfi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportyfi.entity.Venues;
import com.sportyfi.services.VenueService;

@RestController
@RequestMapping("/sportyfi/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping("/approve/{venueRequestId}")
    public ResponseEntity<Venues> approveVenue(@PathVariable UUID venueRequestId) {
        Venues approvedVenue = venueService.approveVenueRequest(venueRequestId);
        return ResponseEntity.ok(approvedVenue);
    }
    
//    @GetMapping
//    public ResponseEntity<List<Venues>> getAll() {
//    	System.out.println("this is getting called");
//        return ResponseEntity.ok(venueService.getAll());
//    }
    
    // errrrrrrrrrrrooooooooorrrrrrrrrrrr in dao
    @GetMapping
    public List<Venues> getVenues(
            @RequestParam Optional<String> sport,
            @RequestParam Optional<String> location,
            @RequestParam Optional<Integer> minPrice,
            @RequestParam Optional<Integer> maxPrice,
            @RequestParam(name = "search") Optional<String> searchQuery
    ) {
        return venueService.findVenues(sport, location, minPrice, maxPrice, searchQuery);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Venues> getVenueById(@PathVariable UUID id) {
        return ResponseEntity.ok(venueService.getById(id));
    }
}

