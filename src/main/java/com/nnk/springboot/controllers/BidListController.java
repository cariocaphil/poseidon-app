package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;

@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<Bid> bids = bidListService.findAll();
        Logger.info("Loaded {} bids for display.", bids.size());
        model.addAttribute("bidLists", bids);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        model.addAttribute("username", username);
        return "bidList/list";
    }

    @GetMapping("/bidList/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable("id") Integer id) {
        Logger.info("Fetching bid with id {}", id);
        return bidListService.findById(id)
            .map(bid -> {
                Logger.info("Found bid: {}", bid);
                return ResponseEntity.ok(bid);
            })
            .orElseGet(() -> {
                Logger.error("Failed to find bid with id {}", id);
                return ResponseEntity.notFound().build();
            });
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        Logger.info("Opening form to add new bid");
        model.addAttribute("bidList", new Bid());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@ModelAttribute("bidList") @Valid Bid bid, BindingResult result, Model model) {
        Logger.info("Validating bid: {}", bid);
        if (!result.hasErrors()) {
            bidListService.save(bid);
            Logger.info("Bid saved successfully, redirecting to list.");
            model.addAttribute("bidLists", bidListService.findAll());
            return "redirect:/bidList/list";
        } else {
            result.getAllErrors().forEach(error ->
                Logger.warn("Validation error: {}", error.getDefaultMessage())
            );
            return "bidList/add";
        }
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Logger.info("Request to update bid with id {}", id);
        Optional<Bid> bidOptional = bidListService.findById(id);
        if (bidOptional.isPresent()) {
            Logger.info("Bid found, displaying update form.");
            model.addAttribute("bidList", bidOptional.get());
            return "bidList/update";
        } else {
            Logger.warn("Bid with id {} not found!", id);
            return "redirect:/bidList/list";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") Bid bid,
        BindingResult result, Model model) {
        Logger.info("Updating bid with id {}", id);
        if (!result.hasErrors()) {
            Optional<Bid> existingBid = bidListService.findById(id);
            if (existingBid.isPresent()) {
                bidListService.update(bid);
                Logger.info("Bid updated successfully.");
                model.addAttribute("bidLists", bidListService.findAll());
                return "redirect:/bidList/list";
            } else {
                Logger.error("Attempted to update non-existing bid with id {}", id);
                return "bidList/update";
            }
        } else {
            Logger.error("Validation errors occurred during updating bid with id {}", id);
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
