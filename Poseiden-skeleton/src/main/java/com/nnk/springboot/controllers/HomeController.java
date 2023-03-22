package com.nnk.springboot.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model, Principal user)
	{
		if (user != null){
			addUsernameToModel(model, user.getName());
		} else {
			addUsernameToModel(model, "No User Authenticated");
		}
		return "home";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}

	private Model addUsernameToModel(Model model, String username) {
		return model.addAttribute("remoteUser", username);
	}
}
