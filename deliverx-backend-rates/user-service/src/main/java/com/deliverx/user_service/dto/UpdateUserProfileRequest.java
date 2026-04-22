package com.deliverx.user_service.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateUserProfileRequest {

    @Size(max = 120)
    private String fullName;

    @Size(max = 30)
    @Pattern(
            regexp = "^$|^[+0-9()\\-\\s]{6,30}$",
            message = "phone should contain only digits, spaces, +, -, () and be at least 6 chars"
    )
    private String phone;

    @Size(max = 80)
    private String city;

    @Size(max = 80)
    private String timezone;

    @Size(max = 500)
    private String about;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
