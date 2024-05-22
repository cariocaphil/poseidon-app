package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        Collections.emptyList());
  }

  public void registerUser(com.nnk.springboot.domain.UserRegistrationRequest request) {
    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      // throw new UserRegistrationException("User with userName " + request.getUsername() + " already exists");
    }

    User newUser = new User();
    newUser.setUserName(request.getUsername());;
    newUser.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(newUser);
  }

}
