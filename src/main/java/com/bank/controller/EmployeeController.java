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
	
	 @GetMapping("")
	    public String mhome(Model model) {
	        return "redirect:employee/dashboard";
	    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	List<Transaction> tList= tr.findAll();
        model.addAttribute("transactions", tList);
        return "employee/home";

        }
    
    @GetMapping("/pendingtransactions")
    public String pendingtransactions(Model model) {
    	List<Transaction> tList= tr.findByStatus("PENDING");
        model.addAttribute("transactions", tList);
        return "employee/pendingtransactions";
    }  
    
    @GetMapping("/approvedtransactions")
    public String approvedtransactions(Model model) {
    	List<Transaction> tList= tr.findByStatus("APPROVED");
        model.addAttribute("transactions", tList);
        return "employee/approvedtransactions";
    }  

    @GetMapping("/declinedtransactions")
    public String declinedtransactions(Model model) {
    	List<Transaction> tList= tr.findByStatus("DISAPPROVED");
        model.addAttribute("transactions", tList);
        return "employee/declinedtransactions";
    }  
    

}