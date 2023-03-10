package com.nnk.springboot.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("home")
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model)
	{

		model.addAttribute("testt", "valuee");
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		model.addAttribute("testt", "valuee");
		return "redirect:/bidList/list";
	}


}
