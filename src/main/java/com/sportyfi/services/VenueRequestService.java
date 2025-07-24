package com.sportyfi.services;

import com.sportyfi.entity.VenueRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface VenueRequestService {
    void createRequest(VenueRequest request);
    VenueRequest getById(UUID id);
    List<VenueRequest> getAll();
    void updateRequest(VenueRequest request);
    void deleteRequest(UUID id);
	void createVenueRequest(VenueRequest venueRequestDto, List<MultipartFile> images);
}
