package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.tinylog.Logger;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model) {
        Logger.info("Accessing the list of users");
        model.addAttribute("users", userRepository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        model.addAttribute("username", username);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user, Model model) {
        Logger.info("Opening form to add a new user");
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error ->
                Logger.warn("Validation error inside: {}", error.getDefaultMessage())
            );
            return "user/add";
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("user", user);
            Logger.error("User registration failed: username {} already exists", user.getUsername());
            return "user/add";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/user/list";
    }


    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Logger.info("Requested update form for user ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            Logger.error("User not found for ID: {}", id);
            return new IllegalArgumentException("Invalid user Id:" + id);
        });
        user.setPassword(""); // Clear password for security reasons
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
        Logger.info("Updating user ID: {}", id);
        if (result.hasErrors()) {
            Logger.warn("Validation errors while updating user ID: {}", id);
            return "user/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        Logger.info("User updated successfully, ID: {}", user.getId());
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        Logger.info("Deleting user ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            Logger.error("User not found for ID: {}", id);
            return new IllegalArgumentException("Invalid user Id:" + id);
        });
        userRepository.delete(user);
        Logger.info("User deleted successfully, ID: {}", id);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
