package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.CustomUserDetails;
import com.bank.service.OTPService;

@RestController

public class TransactionController {
	@Autowired
    private JavaMailSender sender;
	

	@Autowired
	public OTPService otpService;
	
    @RequestMapping(value = "/otpgen", method = RequestMethod.POST)
    @ResponseBody
	public int genarateOTP() {
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
	public int matchOTP(@RequestParam("otp") String otp) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomUserDetails) {
			String username = ((CustomUserDetails)principal).getName();
			int getotp = otpService.getOtp(username);
			if (otp.matches("[0-9]+") && otp.length() == 6)
				if(getotp==Integer.parseInt(otp)) {
					otpService.clearOTP(username);
					System.out.println(transact());
					return 1;
				}
		}
		return 0;
	}
    
    private int transact() {
    	return 1;
    }
	
    private void sendEmail(String email, String messagecontent, String subject) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(email);
        message.setText(messagecontent);
        message.setSubject(subject);
        sender.send(message);
        
    }
}
