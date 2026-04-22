package com.deliverx.user_service.controller;

import com.deliverx.user_service.dto.UpdateUserProfileRequest;
import com.deliverx.user_service.dto.UserProfileResponse;
import com.deliverx.user_service.entity.UserProfile;
import com.deliverx.user_service.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserProfileService userProfileService;

    public UserController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/me")
    public UserProfileResponse getMyProfile(Authentication authentication) {
        UserProfile profile = userProfileService.getOrCreateByEmail(authentication.getName());
        return UserProfileResponse.from(profile);
    }

    @PutMapping("/me")
    public UserProfileResponse updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        UserProfile profile = userProfileService.updateProfile(authentication.getName(), request);
        return UserProfileResponse.from(profile);
    }
}
