package com.kce.controller;

import com.kce.dto.LoginRequest;
import com.kce.dto.LoginResponse;
import com.kce.repository.UserRepository;
import com.kce.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.kce.entity.User;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;
@Autowired
private UserRepository userRepository;
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority());
        String role = jwtUtil.extractRole(token);
        // Fetch unique_id directly from UserAuth table
        String uniqueId = userRepository
                .findByEmail(userDetails.getUsername())
                .map(User::getUniqueId) // assuming getter is getUniqueId()
                .orElse(null);

        return new LoginResponse(token, role, userDetails.getUsername(), uniqueId); // <-- send email
    }

}

