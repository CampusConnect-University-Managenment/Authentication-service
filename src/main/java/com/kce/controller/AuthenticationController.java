package com.kce.controller;

import com.kce.dto.LoginRequest;
import com.kce.dto.LoginResponse;
import com.kce.repository.UserRepository;
import com.kce.service.CustomUserDetails;
import com.kce.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.kce.entity.User;

import java.util.Map;

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

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority()
        );

        return new LoginResponse(
                token,
                userDetails.getAuthorities().iterator().next().getAuthority(),
                userDetails.getUsername(),
                userDetails.getUniqueId() // ✅ this now comes from CustomUserDetails
        );
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    @PutMapping("/update-password/{uniqueId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable String uniqueId,
            @RequestBody Map<String, String> request) {

        String newPassword = request.get("newPassword");

        User user = userRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password Changed Successfully");
    }


}

