package com.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private int id;
    @Column(name = "from_account_no")
    private String from;
    @Column(name = "to_account_no")
    private String to;
    @Column(name = "amount")
    private double amount;
    @Column(name = "message")
    private String message;
    @Column(name = "time")
    private String time;
    @Column(name = "approval_status")
    private String status;
    
    public Transaction() {
    	
    }

	public Transaction(Transaction transaction) {
		this.id =transaction.getId();
		this.from = transaction.getFrom();
		this.to = transaction.getTo();
		this.amount = transaction.getAmount();
		this.message = transaction.getMessage();
		this.time = transaction.getTime();
		this.status = transaction.getStatus();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
