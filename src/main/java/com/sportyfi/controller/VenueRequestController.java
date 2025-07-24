package com.sportyfi.controller;

import com.sportyfi.entity.VenueRequest;
import com.sportyfi.services.VenueRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sportyfi/venues")
public class VenueRequestController {

    @Autowired
    private VenueRequestService service;

    @PostMapping("/request")
    public ResponseEntity<?> create(@RequestBody VenueRequest request) {
    	System.out.println(request);
        service.createRequest(request);
        return ResponseEntity.ok("Venue request created");
    }
    
    @PostMapping("/venue-request")
    public ResponseEntity<?> submitVenueRequest(
        @RequestPart("venue") VenueRequest venueRequestDto,
        @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
    	service.createVenueRequest(venueRequestDto, images);
        return ResponseEntity.ok("Venue Request submitted!");
    }


    @GetMapping("/{id}")
    public ResponseEntity<VenueRequest> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<VenueRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody VenueRequest request) {
        service.updateRequest(request);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.deleteRequest(id);
        return ResponseEntity.ok("Deleted");
    }
}
