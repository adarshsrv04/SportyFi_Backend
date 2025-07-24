package com.sportyfi.dao;

import com.sportyfi.entity.Profiles;
import java.util.UUID;

public interface ProfilesDao {
    Profiles findByUserId(UUID userId);
    boolean updateProfile(UUID userId, Profiles profile);
}
