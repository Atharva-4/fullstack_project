package com.example1.login.controller;

import com.example1.login.model.User;
import com.example1.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
// IMPORTANT: Allows requests from your Angular app's origin (default is localhost:4200)
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    // Endpoint for registering a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken!");
        }

        // !! SECURITY WARNING !!
        // In a real application, you MUST hash the password before saving!
        // Example: user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        // Find user by username
        Optional<User> optionalUser = userRepository.findByUsername(loginDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // !! SECURITY WARNING !!
            // In a real app, you would compare the hashed password.
            // Example: if (passwordEncoder.matches(loginDetails.getPassword(), user.getPassword()))
            if (loginDetails.getPassword().equals(user.getPassword())) {
                // Passwords match
                return ResponseEntity.ok("Login successful!");
            } else {
                // Passwords do not match
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}