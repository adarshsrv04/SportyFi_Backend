package com.sportyfi.controller;

import com.sportyfi.entity.VenueRequest;
import com.sportyfi.entity.Venues;
import com.sportyfi.services.VenueService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    @GetMapping
    public ResponseEntity<List<Venues>> getAll() {
        return ResponseEntity.ok(venueService.getAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Venues> getVenueById(@PathVariable UUID id) {
        return ResponseEntity.ok(venueService.getById(id));
    }
}

