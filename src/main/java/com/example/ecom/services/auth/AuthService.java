package com.example.ecom.services.auth;

import com.example.ecom.dto.SignupRequest;
import com.example.ecom.dto.UserDto;

public interface AuthService {
    public UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
