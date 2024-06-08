package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.tinylog.Logger;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        Logger.info("Accessing the list of trades");
        model.addAttribute("trades", tradeService.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        model.addAttribute("username", username);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        Logger.info("Opening form to add a new trade");
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@ModelAttribute("trade") @Valid Trade trade, BindingResult result, Model model) {
        Logger.info("Validating new trade");
        if (result.hasErrors()) {
            Logger.warn("Validation errors while adding trade: {}", trade);
            return "trade/add";
        }
        tradeService.save(trade);
        Logger.info("Trade saved successfully: {}", trade.getTradeId());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Logger.info("Requested update form for trade ID: {}", id);
        Trade trade = tradeService.findById(id)
            .orElseThrow(() -> {
                Logger.error("Trade not found for ID: {}", id);
                return new IllegalArgumentException("Invalid trade Id:" + id);
            });
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Long id, @Valid Trade trade,
        BindingResult result, Model model) {
        Logger.info("Updating trade ID: {}", id);
        if (result.hasErrors()) {
            Logger.warn("Validation errors while updating trade ID: {}", id);
            return "trade/update";
        }
        trade.setTradeId(Math.toIntExact(id));
        tradeService.save(trade);
        Logger.info("Trade updated successfully, ID: {}", trade.getTradeId());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Long id, Model model) {
        Logger.info("Deleting trade ID: {}", id);
        Trade trade = tradeService.findById(id)
            .orElseThrow(() -> {
                Logger.error("Trade not found for ID: {}", id);
                return new IllegalArgumentException("Invalid trade Id:" + id);
            });
        tradeService.delete(trade);
        Logger.info("Trade deleted successfully, ID: {}", id);
        return "redirect:/trade/list";
    }
}
