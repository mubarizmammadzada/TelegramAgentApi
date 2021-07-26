package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.LoginPostDto;
import com.example.telegramagentapi.dtos.RegisterPostDto;
import com.example.telegramagentapi.dtos.RegisterResponseDto;
import com.example.telegramagentapi.dtos.UpdatePasswordDto;
import org.keycloak.representations.AccessTokenResponse;

public interface UserService {
    /**
     * Register new user via keycloak
     * @param user
     * @return
     */
    RegisterResponseDto register(RegisterPostDto user);

    /**
     * Login via keycloak
     * @param user
     * @return
     */
    AccessTokenResponse login(LoginPostDto user);
    void confirmAccount(String tokenStr);
    void updatePassword(UpdatePasswordDto passwordDto);
}
