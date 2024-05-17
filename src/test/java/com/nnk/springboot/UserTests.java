package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void userTest() {
    User user = new User();
    user.setUsername("newUser");
    user.setPassword("password"); // Normally, you'd encode this
    user.setFullname("New User Fullname");
    user.setRole("ADMIN");

    // Save the user
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    user = userRepository.save(user);
    Assert.assertNotNull(user.getId());
    Assert.assertEquals("newUser", user.getUsername());

    // Update the user
    user.setUsername("updatedUser");
    user = userRepository.save(user);
    Assert.assertEquals("updatedUser", user.getUsername());

    // Find
    List<User> users = userRepository.findAll();
    Assert.assertFalse(users.isEmpty());

    // Delete
    Integer id = user.getId();
    userRepository.delete(user);
    Optional<User> deletedUser = userRepository.findById(id);
    Assert.assertFalse(deletedUser.isPresent());
  }
}
