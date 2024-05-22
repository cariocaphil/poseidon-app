package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/login", "/user/list", "user/validate", "/css/**").permitAll() // Allow access to registration, login
            .anyRequest().authenticated()) // All other requests require authentication
        .formLogin(formLogin -> formLogin
            .defaultSuccessUrl("/", true) // Redirect to the user list upon successful login
            .permitAll()) // Allow all to access login page
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Use GET method for logout if CSRF is disabled
            .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
            .permitAll()) // Allow all to access logout
        .sessionManagement(session -> session
            .maximumSessions(1) // This ensures a user can only have one session active at a time
            .expiredUrl("/login?expired")); // Redirect to login page if session expires

    return http.build();
  }
}
