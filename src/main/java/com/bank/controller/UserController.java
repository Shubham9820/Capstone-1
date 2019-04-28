package com.bank.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.model.CustomUserDetails;
import com.bank.model.Transaction;
import com.bank.model.UserAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.BalanceRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UsersRepository;

@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER')")
@Controller
public class UserController {

	@Autowired
	AccountRepository ar;
	@Autowired
	UsersRepository ur;
	@Autowired
	TransactionRepository tr;
	@Autowired
	BalanceRepository br;
    
    @GetMapping("/details")
    public String home(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
		if (principal instanceof CustomUserDetails) {
			String username = ((CustomUserDetails)principal).getName();
    		int userId = ((CustomUserDetails)principal).getUserId();
    		UserAccount a= ar.findFirstByUserId(userId);
    		model.addAttribute("UserDetail", a);
    		model.addAttribute("username", username);
    		System.out.println(br.findByAccountNumber(a.getAccount_no()).get().getBalance());
  		}
        return "user/details";
    }
    
    
    @GetMapping("/transactions")
    public String transaction(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
		if (principal instanceof CustomUserDetails) {
    		int userId = ((CustomUserDetails)principal).getUserId();
    		UserAccount a= ar.findFirstByUserId(userId);
    		String accountNo= a.getAccount_no();
        	List<Transaction> tList= tr.findByFromOrTo(accountNo,accountNo);
        	List<Transaction> newList= new ArrayList();
        	for(Transaction t : tList) {
        		if(t.getFrom().equalsIgnoreCase(accountNo))
        			t.setFrom("You");
        		if(t.getTo().equalsIgnoreCase(accountNo))
        			t.setTo("You");
        		newList.add(t);
        		
        	}
            model.addAttribute("transactions", newList);
  		}

		return "user/transactions";
    }
    
    @GetMapping("/transfer")
    public String transfer(Model model) {

		return "user/transfer";
    }
    

    
    


}