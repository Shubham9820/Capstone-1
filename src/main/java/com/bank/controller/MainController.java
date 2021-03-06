package com.bank.controller;

import com.bank.repository.*;
import com.bank.model.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
	
	@Autowired
	UserRoleRepository ur;	
	
	@RequestMapping("")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/success")
	public String success() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomUserDetails) {
    		int userId = ((CustomUserDetails)principal).getUserId();
    		UserRole u=ur.getOne(userId);
    		int roleId=u.getRoleId();
    		switch (roleId) {
    		
    			case 1: return "redirect:/admin";

    			case 2: return "redirect:/employee";
    		
    			case 3: return "redirect:/user";
    		}
		}
		return "home";
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
	@RequestMapping("/login")
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(auth instanceof AnonymousAuthenticationToken))
		    return "redirect:/";
		
		return "login";
	  }
	
	  @RequestMapping("/login-error")
	  public String loginError(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(auth instanceof AnonymousAuthenticationToken))
			return "redirect:/";
		
	    model.addAttribute("loginError", true);
	    return "login";
	  }
}
