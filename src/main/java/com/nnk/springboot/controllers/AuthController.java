package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tinylog.Logger;

@Controller
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody com.nnk.springboot.domain.UserRegistrationRequest request) {
    Logger.info("Attempting to register new user with email: {}", request.getUsername());
    try {
      userService.registerUser(request);

      Logger.info("User registered successfully with email: {}", request.getUsername());
      return ResponseEntity.ok("User registered successfully");
    } catch (Exception e) {
      Logger.error("Error during user registration for email: {}", request.getUsername(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error during user registration");
    }
  }

}
