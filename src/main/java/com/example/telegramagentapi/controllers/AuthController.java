package com.example.telegramagentapi.controllers;

import com.example.telegramagentapi.dtos.*;
import com.example.telegramagentapi.services.UserService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/users")
@RestController
public class AuthController {

    UserService service;

    public AuthController(UserService service){
        this.service = service;
    }


    @PostMapping(path = "/create")
    public ResponseEntity<RegisterResponseDto> createUser(@RequestBody RegisterPostDto userDTO) {
        return new ResponseEntity<>(service.register(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<AccessTokenResponse> signIn(@RequestBody LoginPostDto loginDto) {
        return new ResponseEntity<>(service.login(loginDto), HttpStatus.OK);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity confirmUserAccount(@RequestParam("token")String confirmationToken){
        service.confirmAccount(confirmationToken);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/update-password")
    public ResponseEntity updatePassword(@RequestBody UpdatePasswordDto passwordDto){
        service.updatePassword(passwordDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
