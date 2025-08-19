package com.kce.controller;

import com.kce.dto.RegisterRequest;
import com.kce.entity.User;
import com.kce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Correct injection

//    @PostMapping("register")
//    public String registerUser(@RequestBody RegisterRequest request) {
//        User user = new User();
//        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole(request.getRole());
//
//        userRepository.save(user);
//        return "User registered successfully!";
//    }
@PostMapping("/register-user")
public String registerStudent(@RequestBody RegisterRequest request) {
    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setUnique_id(request.getUniqueId());

    // Determine role based on roll number prefix
    String uniqueId = request.getUniqueId().toUpperCase(); // normalize to avoid case issues
    if (uniqueId.startsWith("FAC")) {
        user.setRole("faculty");
    } else if (uniqueId.startsWith("AD")) {
        user.setRole("admin");
    } else {
        user.setRole("student");
    }

    userRepository.save(user);
    return "User registered successfully as " + user.getRole() + "!";
}
    @DeleteMapping("/delete-user/{uniqueId}")
    public ResponseEntity<String> deleteUser(@PathVariable String uniqueId) {
        return userRepository.findByUniqueId(uniqueId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok("User deleted successfully from Auth Service");
                })
                .orElse(ResponseEntity.status(404).body("User not found in Auth Service"));
    }

}