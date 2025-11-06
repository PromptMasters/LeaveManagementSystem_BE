package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.LoginRequest;
import org.example.security.JwtIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and JWT token issuance")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtIssuer jwtIssuer;

    @Operation(summary = "User login", description = "Authenticate user credentials and issue JWT access token.", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful, returns username, role, and JWT token", content = @Content(schema = @Schema(example = """
                    {
                      "message": "Đăng nhập thành công",
                      "username": "john_doe",
                      "role": "EMPLOYEE",
                      "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
                    }
                    """))),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User principal = (User) authentication.getPrincipal();

        String role = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_EMPLOYEE");

        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        String token = jwtIssuer.issue(principal.getUsername(), role);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Đăng nhập thành công");
        response.put("username", principal.getUsername());
        response.put("role", role);
        response.put("access_token", token);

        return ResponseEntity.ok(response);
    }
}
