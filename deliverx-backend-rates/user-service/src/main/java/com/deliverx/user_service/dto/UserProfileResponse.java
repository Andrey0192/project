package com.deliverx.user_service.dto;

import com.deliverx.user_service.entity.UserProfile;

import java.time.Instant;

public record UserProfileResponse(
        Long id,
        String email,
        String fullName,
        String phone,
        String city,
        String timezone,
        String about,
        Instant createdAt,
        Instant updatedAt
) {
    public static UserProfileResponse from(UserProfile profile) {
        return new UserProfileResponse(
                profile.getId(),
                profile.getEmail(),
                profile.getFullName(),
                profile.getPhone(),
                profile.getCity(),
                profile.getTimezone(),
                profile.getAbout(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
