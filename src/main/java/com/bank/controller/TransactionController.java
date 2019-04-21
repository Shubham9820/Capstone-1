package com.bank.controller;

import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bank.model.CustomUserDetails;
import com.bank.model.Transaction;
import com.bank.model.UserAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UsersRepository;
import com.bank.service.OTPService;
import com.bank.service.TransactionService;

@Controller

public class TransactionController {
	@Autowired
    private JavaMailSender sender;
	
	@Autowired
	TransactionService ts;
	
	@Autowired
	public OTPService otpService;
	
	@Autowired
	AccountRepository ar;
	
    @RequestMapping("/transact")
    public String transaction(HttpSession session, 
    							@RequestParam("amount") String amount, 
    							@RequestParam("to") String to,
    							@RequestParam("message") String message, 
    							RedirectAttributes redirectAttributes) {
    	
    	Transaction tr=new Transaction();
    	tr.setAmount(Double.parseDouble(amount));
    	tr.setTo(to);
    	tr.setMessage(message);
    	String randd=randomString(64);													//generate random string for verification of page source
    	redirectAttributes.addFlashAttribute("transactionKey", randd);					//add flash attribute of random string
        session.setAttribute("tData", tr);
        return "redirect:/otp?key="+randd;												//put random string in URL attribute
    }
	
	
    @RequestMapping("/otp")
    public String otp(HttpSession session,
    					HttpServletResponse response, 
    					@ModelAttribute("transactionKey") String key,
    					@RequestParam(name = "key")  Optional<String> keyCheck ) {
    	
    	//Match URL attribute with flash attribute to see if page is valid 
		if(key.length()==64 && key.equals(keyCheck.get()) ) {
			int otpCount=0;
			int matchCount=0;
			session.setAttribute("otpCount", otpCount);
			session.setAttribute("matchCount", matchCount);
			return "user/otptest";
		}
		
		response.setStatus(403);
		return "accessDenied";
    }
	
	
    @RequestMapping(value = "/otpgen", method = RequestMethod.POST)
    @ResponseBody
	public int genarateOTP(HttpSession session) {
    	try {
    		int count=(int) session.getAttribute("otpCount");
    		count++;
    		session.setAttribute("otpCount", count);
    		if(count>3) {
    			invalidator(session);
    			return -1;
    		}
    	}
    	catch(NullPointerException e) {
  		  throw new AccessDeniedException("403");  
  		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomUserDetails) {
			String username = ((CustomUserDetails)principal).getName();
			int otp = otpService.generateOTP(username);
			System.out.println(otp);
		}
		
		return 0;
	}
    
    @RequestMapping(value = "/otpmatch", method = RequestMethod.POST)
    @ResponseBody
	public int matchOTP(HttpSession session,@RequestParam("otp") String otp) {
   
    	try {
        	int count=(int) session.getAttribute("matchCount");
        	count++;
        	session.setAttribute("matchCount", count);
        	if(count>3) {
        		invalidator(session);
        		return -1;
        	}
    	}
    	catch(NullPointerException e) {
    		throw new AccessDeniedException("403");  
    	}

        Transaction tr = (Transaction) session.getAttribute("tData"); 
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomUserDetails) {
			String username = ((CustomUserDetails)principal).getName();
    		int userId = ((CustomUserDetails)principal).getUserId();
    		UserAccount a= ar.findFirstByUserId(userId);
    		tr.setFrom(a.getAccount_no());
    		int getotp = otpService.getOtp(username);
			if (otp.matches("[0-9]+") && otp.length() == 6)
				if(getotp==Integer.parseInt(otp)) {
					otpService.clearOTP(username);
					System.out.println(transact(tr));
					invalidator(session);
					return 1;
				}
		}
		return 0;
	}

    
    private String randomString(int size) {
    	StringBuilder sb = new  StringBuilder(""); 
		Random rand = new Random();
    	for (int i = 0; i < size; i++) {
    		int randd = rand.nextInt(0xF);
			sb.append(Integer.toHexString(randd));
		}
    	String str=sb.toString();
    	return str;
    }
    

    private void invalidator(HttpSession session) {
    	session.removeAttribute("tData");
    	session.removeAttribute("otpCount");
    	session.removeAttribute("matchCount");
    }
    
    private int transact(Transaction tr) {
    	ts.save(tr);
    	return 2;
    }
	
    private void sendEmail(String email, String messagecontent, String subject) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(email);
        message.setText(messagecontent);
        message.setSubject(subject);
        sender.send(message);
        
    }
}
