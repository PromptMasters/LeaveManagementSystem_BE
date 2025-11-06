package org.example.service;

import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + username);

        // Lấy user từ database - đây là entity org.example.model.User
        org.example.model.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("User not found in database: " + username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        System.out.println("User found - Username: " + user.getUsername() + ", Role: " + user.getRole());

        // Return Spring Security User - chỉ định rõ package
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Phải là BCrypt hash
                .authorities("ROLE_" + user.getRole())
                .build();
    }
}