package com.lms.controller;

import com.lms.security.jwt.JwtUtil;
import com.lms.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {
    private final Logger logger = LoggerFactory.getLogger(TokenController.class);
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        try {
            // Remove "Bearer " prefix if present
            String refreshToken = refreshTokenHeader.replace("Bearer ", "");

            // Validate refresh token
            if (!jwtUtil.isTokenValid(refreshToken)) {
                logger.warn("Invalid refresh token");
                return ResponseEntity.status(401).body("Invalid or expired refresh token");
            }

            // Extract username/email from refresh token
            String username = jwtUtil.getUsernameFromToken(refreshToken);

            // Load user details
            var userDetails = userDetailsService.loadUserByUsername(username);

            // Generate new access token
            String newAccessToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails); // Optional: refresh refresh-token too

            logger.info("Access token refreshed for user: {}", username);

            return ResponseEntity.ok()
                    .body(
                            new TokenRefreshResponse(newAccessToken, newRefreshToken)
                    );

        } catch (Exception e) {
            logger.error("Failed to refresh token", e);
            return ResponseEntity.status(500).body("Failed to refresh token: " + e.getMessage());
        }
    }

    // Simple DTO class for response
    private record TokenRefreshResponse(String accessToken, String refreshToken) {}

}
