package com.bank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.model.Balance;
import com.bank.model.Transaction;
import com.bank.model.UserAccount;
import com.bank.model.UserRole;
import com.bank.model.Users;
import com.bank.repository.AccountRepository;
import com.bank.repository.BalanceRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRoleRepository;
import com.bank.repository.UsersRepository;

@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
@Controller
public class AdminController {
	
	@Autowired
	private AccountRepository ar;
	@Autowired
	private TransactionRepository tr;
	@Autowired
	private UsersRepository ur;
	@Autowired
	private UserRoleRepository uror;
	@Autowired
	private BalanceRepository br;
	
	 @GetMapping("")
	    public String mhome(Model model) {
	        return "redirect:admin/dashboard";
	    }
	
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpServletRequest request) {
    	List<Transaction> tList= tr.findAll();
    	model.addAttribute("transactions",tList);
        return "admin/home";
    }
    
    @GetMapping("/userProfiles")
    public String manageUsers(Model model, HttpServletRequest request) {
    	List<UserAccount> aList= ar.findAll();
    	List<UserAccount> newList= new ArrayList<UserAccount>();

    	for(UserAccount account : aList) {
    		Users u= ur.findOne(account.getUser_id());
    		account.setEmail(u.getName());
    		account.setAddress(u.getPassword());
    		newList.add(account);
    	}

    	model.addAttribute("users",newList);
    	return "admin/userProfiles";
    }
    
    @GetMapping("/employeeProfiles")
    public String manageEmployees(Model model) {
    	List<Transaction> tList= tr.findAll();
    	model.addAttribute("transactions",tList);
    	return "admin/employeeProfiles";
    }
    
    @GetMapping("/adduser")
    public String adduser(Model model) {
    	return "admin/adduser";
    }
    
    @GetMapping("/addemployee")
    public String addemployee(Model model) {
    	return "admin/addemployee";
    }
    
    @GetMapping("/addadmin")
    public String addadmin(Model model) {
    	return "admin/addadmin";
    }
    
    @RequestMapping("/saveuser")
    public String saveuser(Model model,
    		@RequestParam("ano") String ano,
    		@RequestParam("fname") String fname,
    		@RequestParam("lname") String lname,
    		@RequestParam("mobile") String mobile,
    		@RequestParam("email") String email,
    		@RequestParam("city") String city,
    		@RequestParam("state") String state,
    		@RequestParam("address") String address,
    		@RequestParam("country") String country) {

    	UserAccount ua= new UserAccount();
    	Users u= new Users();
    	UserRole urol= new UserRole();
    	Balance bl= new Balance();
    	
    	
    	String password=randomPass();
    	System.out.println(password);
    	u.setName(ano);
    	u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt() ));

    	ur.save(u);
    	
    	u=ur.findByName(ano).get();
    	
    	urol.setUserId(u.getId());
    	urol.setRoleId(3);
    	
    	bl.setAccountNumber(ano);
    	bl.setBalance(0);
    	    	
    	ua.setUser_id(u.getId());
    	ua.setAccountNo(ano);
    	ua.setAddress(address);
    	ua.setCity(city);
    	ua.setCountry(country);
    	ua.setEmail(email);
    	ua.setFirst_name(fname);
    	ua.setLast_name(lname);
    	ua.setState(state);
    	ua.setMobile(Long.parseLong(mobile));
    	
    	uror.save(urol);
    	ar.save(ua);
    	br.save(bl);
    	
    	
    	return "redirect:adduser";
    }
    
    @RequestMapping("/saveemployee")
    public String saveemployee(Model model,
    		@RequestParam("eId") String ename,
    		@RequestParam("password") String password) {

    	Users u= new Users();
    	UserRole urol= new UserRole();
    	    	
    	u.setName(ename);
    	u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt() ));

    	ur.save(u);
    	
    	u=ur.findByName(ename).get();
    	
    	urol.setUserId(u.getId());
    	urol.setRoleId(2);
    	
    	uror.save(urol);
    	
    	return "redirect:addemployee";
    }
    
    @RequestMapping("/saveadmin")
    public String saveadmin(Model model,
    		@RequestParam("eId") String aname,
    		@RequestParam("password") String password) {

    	Users u= new Users();
    	UserRole urol= new UserRole();
    	    	
    	u.setName(aname);
    	u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt() ));

    	ur.save(u);
    	
    	u=ur.findByName(aname).get();
    	
    	urol.setUserId(u.getId());
    	urol.setRoleId(1);
    	
    	uror.save(urol);
    	
    	return "redirect:addadmin";
    }
    
    @RequestMapping("/checkunique")
    @ResponseBody
    public int checkunique(Model model, @RequestParam("aNo") String ano) {
    	if(ar.findOne(ano)==null && !ur.findByName(ano).isPresent())
    		return 1;
    	return 0;
    }
    
    

    
    @RequestMapping("/passwordreset")
    @ResponseBody
    public String passwordreset(Model model,@RequestParam("uId") String uid) {
    	System.out.println(uid);

    	return "0";
    }
    
    private String randomPass() {
    	int size=16;
    	StringBuilder sb = new  StringBuilder(""); 
		Random rand = new Random();
    	for (int i = 0; i < size; i++) {
    		int randd = rand.nextInt(0xF);
			sb.append(Integer.toHexString(randd));
		}
    	String str=sb.toString();
    	return str;
    }
}