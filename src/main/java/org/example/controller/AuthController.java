// package org.example.controller;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.media.Content;
// import io.swagger.v3.oas.annotations.media.Schema;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import lombok.RequiredArgsConstructor;
// import org.example.dto.request.LoginRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.web.bind.annotation.*;

// import java.util.LinkedHashMap;
// import java.util.Map;

// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/api/auth")
// @Tag(name = "Authentication", description = "Endpoints for user
// authentication")
// public class AuthController {

// @Autowired
// private AuthenticationManager authenticationManager;

// @Operation(summary = "User login", description = "Authenticate user
// credentials", responses = {
// @ApiResponse(responseCode = "200", description = "Login successful", content
// = @Content(schema = @Schema(example = """
// {
// "message": "Đăng nhập thành công",
// "username": "john_doe",
// "role": "EMPLOYEE"
// }
// """))),
// @ApiResponse(responseCode = "401", description = "Invalid username or
// password")
// })
// @PostMapping("/login")
// public ResponseEntity<?> login(@RequestBody LoginRequest request) {
// System.out.println("🔐 Login attempt - Username: " + request.getUsername());

// try {
// Authentication authentication = authenticationManager.authenticate(
// new UsernamePasswordAuthenticationToken(request.getUsername(),
// request.getPassword()));

// // Lưu authentication vào SecurityContext
// SecurityContextHolder.getContext().setAuthentication(authentication);

// System.out.println("✅ Authentication successful");

// User principal = (User) authentication.getPrincipal();

// String role = principal.getAuthorities().stream()
// .map(GrantedAuthority::getAuthority)
// .findFirst()
// .orElse("ROLE_EMPLOYEE");

// if (role.startsWith("ROLE_")) {
// role = role.substring(5);
// }

// Map<String, String> response = new LinkedHashMap<>();
// response.put("message", "Đăng nhập thành công");
// response.put("username", principal.getUsername());
// response.put("role", role);

// return ResponseEntity.ok(response);

// } catch (BadCredentialsException e) {
// System.out.println("❌ Bad credentials: " + e.getMessage());

// Map<String, String> error = new LinkedHashMap<>();
// error.put("error", "Tên đăng nhập hoặc mật khẩu không đúng");
// return ResponseEntity.status(401).body(error);

// } catch (Exception e) {
// System.out.println("❌ Unexpected error: " + e.getMessage());
// e.printStackTrace();

// Map<String, String> error = new LinkedHashMap<>();
// error.put("error", "Đăng nhập thất bại: " + e.getMessage());
// return ResponseEntity.status(500).body(error);
// }
// }
// }