package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configures the security aspects of the application, setting up authentication
 * and authorization mechanisms, and defining security behaviors like login,
 * logout, session management, and CSRF protection.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Provides a password encoder bean using BCrypt hashing algorithm.
   * This encoder is used to secure passwords in the application.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the security filter chain that applies to HTTP requests.
   * This method sets up form login, logout configurations, and session management.
   *
   * @param http the {@link HttpSecurity} to configure
   * @return the configured {@link SecurityFilterChain}
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()  // Disabling CSRF protection for API simplicity (not recommended for production)
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/login", "/user/list", "user/validate", "/css/**").permitAll() // Permit all for these paths
            .anyRequest().authenticated()) // Require authentication for all other requests
        .formLogin(formLogin -> formLogin
            .defaultSuccessUrl("/", true) // Redirect to the root upon successful login
            .permitAll()) // Allow access to all users
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Enable GET method for logout
            .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
            .permitAll()) // Allow access to all users
        .sessionManagement(session -> session
            .maximumSessions(1) // Allow only one active session per user
            .expiredUrl("/login?expired")); // Redirect to login page if session expires

    return http.build();
  }
}