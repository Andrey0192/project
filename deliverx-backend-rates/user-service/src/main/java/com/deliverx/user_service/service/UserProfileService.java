package com.deliverx.user_service.service;

import com.deliverx.user_service.dto.UpdateUserProfileRequest;
import com.deliverx.user_service.entity.UserProfile;
import com.deliverx.user_service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public UserProfile getOrCreateByEmail(String email) {
        return userProfileRepository.findByEmail(email)
                .orElseGet(() -> userProfileRepository.save(createEmptyProfile(email)));
    }

    @Transactional
    public UserProfile updateProfile(String email, UpdateUserProfileRequest request) {
        UserProfile profile = getOrCreateByEmail(email);

        profile.setFullName(normalize(request.getFullName()));
        profile.setPhone(normalize(request.getPhone()));
        profile.setCity(normalize(request.getCity()));
        profile.setTimezone(normalize(request.getTimezone()));
        profile.setAbout(normalize(request.getAbout()));

        return userProfileRepository.save(profile);
    }

    private UserProfile createEmptyProfile(String email) {
        UserProfile profile = new UserProfile();
        profile.setEmail(email);
        return profile;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
