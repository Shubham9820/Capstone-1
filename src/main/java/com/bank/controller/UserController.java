package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bank.model.CustomUserDetails;
import com.bank.model.UserAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.UsersRepository;

@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER')")
@Controller
public class UserController {

	@Autowired
	AccountRepository ar;
	UsersRepository ur;
	
    @GetMapping("/home")
    public String home(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
		if (principal instanceof CustomUserDetails) {
			String username = ((CustomUserDetails)principal).getName();
    		int userId = ((CustomUserDetails)principal).getUserId();
    		UserAccount a= ar.findFirstByUserId(userId);
    		model.addAttribute("UserDetail", a);
    		model.addAttribute("username", username);

  		}
    	
        return "user/details";
    }

}