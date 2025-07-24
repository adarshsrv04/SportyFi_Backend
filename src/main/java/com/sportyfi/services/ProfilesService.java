package com.sportyfi.services;

import com.sportyfi.entity.Profiles;
import java.util.UUID;

public interface ProfilesService {
    Profiles getProfileByUserId(UUID userId);
    boolean updateProfile(UUID userId, Profiles profile);
}
