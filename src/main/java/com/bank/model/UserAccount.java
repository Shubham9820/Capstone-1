package com.bank.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account_detail")
public class UserAccount {

	   	@Id
	    @Column(name = "account_no")
	    private String accountNo;
	    @Column(name = "first_name")
	    private String first_name;
	    @Column(name = "last_name")
	    private String last_name;
	    @Column(name = "user_id")
	    private int userId;
	    @Column(name = "email")
	    private String email;
		@Column(name = "mobile")
	    private long mobile;
	    @Column(name = "address")
	    private String address;
	    @Column(name = "city")
	    private String city;
	    @Column(name = "state")
	    private String state;
	    @Column(name = "country")
	    private String country;
	  
	    public UserAccount() {
	    }

	    public UserAccount(UserAccount usersAccount) {
	        this.accountNo = usersAccount.getAccountNo();
	        this.first_name = usersAccount.getFirst_name();
	        this.last_name = usersAccount.getLast_name();
	        this.userId = usersAccount.getUser_id();
	        this.email = usersAccount.getEmail();
	        this.mobile = usersAccount.getMobile();
	        this.address = usersAccount.getAddress();
	        this.city = usersAccount.getCity();
	        this.state = usersAccount.getState();
	        this.country = usersAccount.getCountry();
	    }
	    
	    public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String account_no) {
			this.accountNo = account_no;
		}
		public String getFirst_name() {
			return first_name;
		}
		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}
		public String getLast_name() {
			return last_name;
		}
		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}
		public int getUser_id() {
			return userId;
		}
		public void setUser_id(int user_id) {
			this.userId = user_id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public long getMobile() {
			return mobile;
		}
		public void setMobile(long mobile) {
			this.mobile = mobile;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
	    
}
