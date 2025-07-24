package com.sportyfi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyfi.dao.ProfilesDao;
import com.sportyfi.entity.Profiles;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfilesServiceImpl implements ProfilesService {

    @Autowired
    private ProfilesDao profilesDao;

    @Override
    public Profiles getProfileByUserId(UUID userId) {
        return profilesDao.findByUserId(userId);
    }

    @Override
    public boolean updateProfile(UUID userId, Profiles profile) {
        return profilesDao.updateProfile(userId, profile);
    }
}
