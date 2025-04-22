package com.we.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.json.JsonFactory;
import com.we.dto.*;
import com.we.exception.ApiException;
import com.we.model.Role;
import com.we.model.User;
import com.we.repository.UserRepository;
import com.we.security.jwt.JwtUtil;
import com.we.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Value("${google.client.id}")
    private String CLIENT_ID;

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

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            // Verify Google ID Token
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//                    new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//                    .setAudience(Collections.singletonList(CLIENT_ID))
//                    .build();

//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//                    new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//                    .setAudience(Collections.singletonList(CLIENT_ID))
//                    .build();

            JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    jsonFactory
            )
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            logger.info("Client ID: {}", CLIENT_ID);
            logger.info("verifier: {}", verifier);
            logger.info("request.getIdToken(): {}", request.getIdToken());
            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            logger.info("Google idToken: {}", idToken);
            if (idToken == null) {
                return ResponseEntity.badRequest().body("Invalid Google token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            logger.info("Email: {}", email);
            logger.info("Name: {}", name);
            // Check if user exists
            User user = userRepository.findByEmail(email).get();
            if (user == null) {
                // New user → Register automatically with default role
                user = new User();
                user.setEmail(email);
                user.setFirstName(name);
                user.setEnabled(true);
                user.setRole(Role.ROLE_USER);
                user.setPassword(null); // No password for Google login
                user = userRepository.save(user);
            }

            String jwtToken = jwtUtil.generateToken(userDetailsService.loadUserByUsername(email));

            return ResponseEntity.ok(LoginResponse.builder()
                    .user(userDetailsService.userToUserDto(user))
                    .token(jwtToken)
                    .build()
            );
        } catch (Exception e) {
            logger.error("Google authentication failed: {}",e.toString());
            return ResponseEntity.badRequest().body("Google authentication failed: " + e.getMessage());
        }
    }

}
