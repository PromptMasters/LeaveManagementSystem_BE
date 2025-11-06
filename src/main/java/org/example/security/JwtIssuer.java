package org.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    public String issue(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Date.from(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS))))
                .withClaim("username", username)
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}