package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.model.CustomUserDetails;
import com.bank.model.EmployeeAccount;
import com.bank.model.UserAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.EmployeeAccountRepository;
import com.bank.repository.UsersRepository;


@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
@Controller
public class AdminController {
	
	@Autowired
	AccountRepository ar;
	EmployeeAccountRepository er;

    @GetMapping("/dashboard")
    @ResponseBody
    public String adminPage(Model model) {
    	/*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if (principal instanceof CustomUserDetails) {
			String username = ((CustomUserDetails)principal).getName();
    		int userId = ((CustomUserDetails)principal).getUserId();
    		EmployeeAccount ea= er.findFirstByUserId(userId);
    		model.addAttribute("UserDetail", ea);
    		model.addAttribute("username", username);

  		}*/
    	
        return "admin dashboard";
    }
    
	/* @RequestMapping("") */

}