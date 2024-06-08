package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import org.tinylog.Logger;

@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        Logger.info("Accessing the list of ratings");
        List<Rating> ratings = ratingService.findAll();
        model.addAttribute("ratings", ratings);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        model.addAttribute("username", username);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        Logger.info("Opening form to add a new rating");
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@ModelAttribute("rating") @Valid Rating rating, BindingResult result, Model model) {
        Logger.info("Validating new rating");
        if (result.hasErrors()) {
            Logger.warn("Validation errors present while adding new rating");
            return "rating/add";
        }
        ratingService.save(rating);
        Logger.info("Rating saved successfully");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Logger.info("Requested update form for rating ID: {}", id);
        Rating rating = ratingService.findById(id)
            .orElseThrow(() -> {
                Logger.error("Rating with ID {} not found", id);
                return new IllegalArgumentException("Invalid rating Id:" + id);
            });
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Long id, @Valid Rating rating, BindingResult result, Model model) {
        Logger.info("Updating rating ID: {}", id);
        if (result.hasErrors()) {
            Logger.warn("Validation errors while updating rating ID: {}", id);
            model.addAttribute("rating", rating);
            return "rating/update";
        }
        rating.setId(id);
        ratingService.save(rating);
        Logger.info("Rating updated successfully, ID: {}", id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        Logger.info("Request to delete rating ID: {}", id);
        Rating rating = ratingService.findById(id)
            .orElseThrow(() -> {
                Logger.error("Rating with ID {} not found", id);
                return new IllegalArgumentException("Invalid rating Id:" + id);
            });
        ratingService.delete(id);
        Logger.info("Rating deleted successfully, ID: {}", id);
        return "redirect:/rating/list";
    }
}
