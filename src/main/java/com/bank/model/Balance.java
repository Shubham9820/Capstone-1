package com.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "balance")
public class Balance {
    @Id
    @Column(name = "account_no")
    private String accountNumber;
    @Column(name = "balance")
    private double balance;
    
    public Balance() {
    }

    public Balance(Balance balance) {
        this.accountNumber = balance.getAccountNumber();
        this.balance = balance.getBalance();

    }

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
