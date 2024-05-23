package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.tinylog.Logger;

@Controller
public class CurveController {
    private CurvePointService curvePointService;

    @Autowired
    public void CurvePointController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePoint> curvePoints = curvePointService.findAll();
        Logger.info("Loaded {} curve points for display.", curvePoints.size());
        model.addAttribute("curvePoints", curvePoints);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        model.addAttribute("username", username);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        Logger.info("Opening form to add new curve point");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        Logger.info("Validating curve point: {}", curvePoint);
        if (!result.hasErrors()) {
            curvePointService.save(curvePoint);
            Logger.info("Curve point saved successfully, redirecting to list.");
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        } else {
            result.getAllErrors().forEach(error -> Logger.warn("Validation error: {}", error.getDefaultMessage()));
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/add";
        }
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Logger.info("Request to update curve point with id {}", id);
        Optional<CurvePoint> curvePointOptional = curvePointService.findById(id);
        if (curvePointOptional.isPresent()) {
            Logger.info("Curve point found, displaying update form.");
            model.addAttribute("curvePoint", curvePointOptional.get());
            return "curvePoint/update";
        } else {
            Logger.warn("Curve point with id {} not found!", id);
            return "redirect:/curvePoint/list";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
        BindingResult result, Model model) {
        Logger.info("Updating curve point with id {}", id);
        if (!result.hasErrors()) {
            curvePoint.setId(id);
            curvePointService.update(curvePoint);
            Logger.info("Curve point updated successfully.");
            return "redirect:/curvePoint/list";
        } else {
            Logger.warn("Validation errors occurred during updating curve point with id {}", id);
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        Logger.info("Deleting curve point with id {}", id);
        Optional<CurvePoint> curvePoint = curvePointService.findById(id);
        if (curvePoint.isPresent()) {
            curvePointService.delete(id);
            Logger.info("Curve point deleted successfully.");
        } else {
            Logger.error("Curve point not found for ID {}", id);
        }
        return "redirect:/curvePoint/list";
    }
}
