package com.sportyfi.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sportyfi.dao.VenueRequestDao;
import com.sportyfi.entity.VenueImage;
import com.sportyfi.entity.VenueRequest;

@Service
public class VenueRequestServiceImpl implements VenueRequestService {

    @Autowired
    private VenueRequestDao dao;

    @Override
    @Transactional
    public void createRequest(VenueRequest request) {
        dao.save(request);
    }
    
    @Transactional
    public void createVenueRequest(VenueRequest venue, List<MultipartFile> images) {
//        VenueRequest venue = dto;

        if (images != null) {
            List<VenueImage> imageEntities = images.stream().map(file -> {
                try {
                    VenueImage img = new VenueImage();
                    img.setData(file.getBytes());
                    img.setVenueRequest(venue);
                    return img;
                } catch (IOException e) {
                    throw new RuntimeException("Error processing image", e);
                }
            }).collect(Collectors.toList());

//            venue.setImages(imageEntities);
        }

        dao.save(venue);
    }


    @Override
    @Transactional(readOnly = true)
    public VenueRequest getById(UUID id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VenueRequest> getAll() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public void updateRequest(VenueRequest request) {
        dao.update(request);
    }

    @Override
    @Transactional
    public void deleteRequest(UUID id) {
        dao.delete(id);
    }
}
