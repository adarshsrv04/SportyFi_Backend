package com.sportyfi.dao;

import com.sportyfi.entity.VenueRequest;
import java.util.List;
import java.util.UUID;

public interface VenueRequestDao {
    void save(VenueRequest request);
    VenueRequest findById(UUID id);
    List<VenueRequest> findAll();
    void update(VenueRequest request);
    void delete(UUID id);
}
