package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserRegistrationException;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tinylog.Logger;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Logger.info("Attempting to load user by username: {}", username);
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> {
          Logger.error("User not found with username: {}", username);
          return new UsernameNotFoundException("User not found with username: " + username);
        });

    Logger.info("User found with username: {}", username);
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        Collections.emptyList());
  }

}
