package com.diplomado.diplomado.config;

import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    public String extractUsername(String token) {
        // Mock implementation for demonstration purposes
        // In a real application, this would parse the JWT token
        if (token != null && !token.isEmpty()) {
            return "user"; // Default mock user
        }
        return null;
    }

    public boolean validateToken(String token, String username) {
        // Mock implementation for demonstration purposes
        // In a real application, this would validate the JWT signature and expiration
        return token != null && !token.isEmpty() && username != null;
    }
}
