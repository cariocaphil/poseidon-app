package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidListService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tinylog.Logger;


@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        List<Bid> bids = bidListService.findAll();
        model.addAttribute("bidLists", bids);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        model.addAttribute("username", username);
        return "bidList/list";
    }

    @GetMapping("/bidList/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable("id") Integer id) {
        return bidListService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
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
            Logger.info("No errors found, saving bid.");
            bidListService.save(bid);
            model.addAttribute("bidLists", bidListService.findAll());
            return "redirect:/bidList/list";
        } else {
            result.getAllErrors().forEach(error ->
                Logger.warn("Validation error: {}", error.getDefaultMessage())
            );
        }

        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Bid> bidOptional = bidListService.findById(id);
        if (bidOptional.isPresent()) {
            model.addAttribute("bidList", bidOptional.get());
            return "bidList/update";
        } else {
            model.addAttribute("errorMessage", "Bid not found");
            return "redirect:/bidList/list";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") Bid bidList,
        BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Optional<Bid> existingBid = bidListService.findById(id);
            if (existingBid.isPresent()) {
                // Update the bid
                bidList.setId(id);
                bidListService.update(bidList);
                model.addAttribute("bidLists", bidListService.findAll());
                return "redirect:/bidList/list";
            } else {
                model.addAttribute("errorMessage", "Bid not found");
                return "bidList/update";
            }
        } else {
            model.addAttribute("bidList", bidList);
            model.addAttribute("errorMessage", "Validation error");
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
