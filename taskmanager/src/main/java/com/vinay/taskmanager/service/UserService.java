package com.vinay.taskmanager.service;

import com.vinay.taskmanager.payload.LoginDto;
import com.vinay.taskmanager.payload.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto register(UserDto userDto);

    String login(LoginDto loginDto);
}
