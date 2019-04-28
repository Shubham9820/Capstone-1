package com.bank.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.model.UserAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.UsersRepository;

@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
@Controller
public class AdminController {
	
	@Autowired
	private AccountRepository ar;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpServletRequest request,@RequestParam("accountNumber") Optional<String> ano) {
//    	String accountNumber = (String)request.getAttribute("accountNumber");
    	String t;
    	if(ano.isPresent())
    		t=ano.get();
    	else
    		t="U101116FCS242";
    	Optional<UserAccount> ua = ar.findFirstByAccountNo(t);
    	UserAccount u = ua.get();
		System.out.println(u.getAccount_no());
    	model.addAttribute("UserDetail",u);
        return "admin/index";
    }
    
    @GetMapping("/dashboard/userProfiles")
    public String manageUsers(Model model, HttpServletRequest request) {
    	List<UserAccount> ua = ar.findAll();
    	model.addAttribute("userList",ua);
    	return "admin/userProfiles";
    }
    
    @GetMapping("/dashboard/employeeProfiles")
    public String manageEmployees(Model model) {
    	//List<EmployeeAccount> ea = empRepo.findAll();
    	//model.addAttribute("empList",ea);
    	return "admin/empProfiles";
    }

}