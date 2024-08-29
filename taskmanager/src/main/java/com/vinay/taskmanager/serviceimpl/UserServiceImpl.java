package com.vinay.taskmanager.serviceimpl;

import com.vinay.taskmanager.entity.User;
import com.vinay.taskmanager.payload.LoginDto;
import com.vinay.taskmanager.payload.UserDto;
import com.vinay.taskmanager.repository.UserRepository;
import com.vinay.taskmanager.security.JWTTokenProvider;
import com.vinay.taskmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Override
    public UserDto register(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = modelMapper.map(userDto,User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication auth = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtTokenProvider.generateToken(auth);
        return token;
    }

}
