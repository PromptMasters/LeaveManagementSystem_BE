package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.LoginRequest;
import org.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

        private final AuthService authService;

        @Operation(summary = "User login", description = "Authenticate username and password. If valid, return user info with role.", responses = {
                        @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(example = """
                                        {
                                          "message": "Đăng nhập thành công",
                                          "username": "employee1",
                                          "userId": 2,
                                          "role": "EMPLOYEE"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(example = """
                                        {
                                          "error": "Tên đăng nhập hoặc mật khẩu không đúng"
                                        }
                                        """)))
        })
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest request) {
                try {
                        Map<String, Object> result = authService.login(request);
                        return ResponseEntity.ok(result);
                } catch (BadCredentialsException e) {
                        return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
                } catch (Exception e) {
                        return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
                }
        }
}
