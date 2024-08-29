package com.vinay.taskmanager.controller;

import com.vinay.taskmanager.payload.JWTAuthResponse;
import com.vinay.taskmanager.payload.LoginDto;
import com.vinay.taskmanager.payload.UserDto;
import com.vinay.taskmanager.security.JWTTokenProvider;
import com.vinay.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.register(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> loginUser(@RequestBody LoginDto loginDto){
        String jwtToken = userService.login(loginDto);
        return new ResponseEntity<>(new JWTAuthResponse(jwtToken),HttpStatus.OK);
    }
}
