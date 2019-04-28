package com.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.model.Transaction;
import com.bank.repository.TransactionRepository;

@PreAuthorize("hasAnyRole('EMPLOYEE')")
@RequestMapping("/employee")
@Controller
public class EmployeeController {
	
	@Autowired
	TransactionRepository tr;

    @GetMapping("/dashboard")
    @ResponseBody
    public String dashboard() {
        return "employee dashboard";
    }
    
    @GetMapping("/transactions")
    public String transaction(Model model) {
    	List<Transaction> tList= tr.findByStatus("PENDING");
        model.addAttribute("transactions", tList);
        return "employee/transactions";
    }  
    

}