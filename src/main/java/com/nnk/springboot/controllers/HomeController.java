package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tinylog.Logger;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home(Model model) {
		Logger.info("Accessing the home page");
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		Logger.info("Admin accessing the admin home page, redirecting to bid list");
		return "redirect:/bidList/list";
	}
}
