package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        Logger.info("Accessing the list of rule names");
        model.addAttribute("ruleNames", ruleNameService.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        model.addAttribute("username", username);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        Logger.info("Opening form to add a new rule name");
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        Logger.info("Validating rule name: {}", ruleName.getName());
        if (result.hasErrors()) {
            Logger.warn("Validation errors present while adding new rule name");
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        Logger.info("Rule name saved successfully");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Logger.info("Requested update form for rule ID: {}", id);
        RuleName ruleName = ruleNameService.findById(id)
            .orElseThrow(() -> {
                Logger.error("Rule name with ID {} not found", id);
                return new IllegalArgumentException("Invalid rule Id:" + id);
            });
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Long id, @Valid RuleName ruleName, BindingResult result, Model model) {
        Logger.info("Updating rule name ID: {}", id);
        if (result.hasErrors()) {
            Logger.warn("Validation errors while updating rule name ID: {}", id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.save(ruleName);
        Logger.info("Rule name updated successfully, ID: {}", id);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Long id, Model model) {
        Logger.info("Request to delete rule name ID: {}", id);
        RuleName ruleName = ruleNameService.findById(id)
            .orElseThrow(() -> {
                Logger.error("Rule name with ID {} not found", id);
                return new IllegalArgumentException("Invalid rule Id:" + id);
            });
        ruleNameService.delete(id);
        Logger.info("Rule name deleted successfully, ID: {}", id);
        return "redirect:/ruleName/list";
    }
}
