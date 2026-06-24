package com.bajaj.bfhl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.user.full-name}")
    private String fullName;

    @Value("${app.user.dob}")
    private String dob;

    @Value("${app.user.email}")
    private String email;

    @Value("${app.user.roll-number}")
    private String rollNumber;

    //Method to fetch user_id 
    public String getUserId() {
        String normalised = fullName.trim().toLowerCase().replaceAll("\\s+", "_");
        return normalised + "_" + dob;
    }

    //Fetch user email
    public String getEmail() {
        return email;
    }

    //Fetch user roll number
    public String getRollNumber() {
        return rollNumber;
    }
}
