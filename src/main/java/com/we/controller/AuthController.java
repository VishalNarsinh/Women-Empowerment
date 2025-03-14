package com.we.controller;

import com.we.dto.LoginRequest;
import com.we.dto.LoginResponse;
import com.we.dto.RegisterRequest;
import com.we.dto.UserDto;
import com.we.exception.ApiException;
import com.we.model.User;
import com.we.security.jwt.JwtUtil;
import com.we.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        UserDto userDto = userDetailsService.saveUser(registerRequest);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder().user(userDetailsService.userToUserDto((User) userDetails)).token(token).build();
        return ResponseEntity.ok(response);
    }

    private void doAuthenticate(String email, String password) {
//        jacksonObjectMapper
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            logger.info("{}",e.getLocalizedMessage());
            throw new ApiException(" Invalid Username or Password !!");
        }

    }


}
