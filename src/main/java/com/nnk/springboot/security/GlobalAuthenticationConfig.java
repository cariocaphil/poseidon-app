package com.nnk.springboot.security;

import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for global authentication settings.
 * This class configures the global authentication manager with user details
 * and password encoding strategies to be used across the application.
 */
@Configuration
public class GlobalAuthenticationConfig {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  /**
   * Constructs a new GlobalAuthenticationConfig with necessary dependencies.
   *
   * @param passwordEncoder the encoder for encoding passwords
   * @param userService the service for loading user-specific data
   */
  @Autowired
  public GlobalAuthenticationConfig(PasswordEncoder passwordEncoder, UserService userService) {
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  /**
   * Configures the global authentication manager to use a custom {@link UserService}
   * for user details and a {@link PasswordEncoder} for password hashing.
   *
   * This configuration is essential for integrating the Spring Security framework
   * with custom user details and password storage mechanisms.
   *
   * @param auth the {@link AuthenticationManagerBuilder} to configure
   * @throws Exception if there is a problem during configuration
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userService)
        .passwordEncoder(passwordEncoder);
  }
}
