package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.LoginRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();

        try {
            // 1️⃣ Authenticate username + password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // 2️⃣ Store context for session-based Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3️⃣ Extract user info
            UserDetails principal = (UserDetails) authentication.getPrincipal();

            String role = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_EMPLOYEE");

            if (role.startsWith("ROLE_")) {
                role = role.substring(5);
            }

            // 4️⃣ Find User entity to get userId
            User user = userRepository.findByUsername(principal.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found in database"));

            response.put("message", "Đăng nhập thành công");
            response.put("username", user.getUsername());
            response.put("userId", user.getId());
            response.put("role", role);

            return response;

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không đúng");
        }
    }
}
