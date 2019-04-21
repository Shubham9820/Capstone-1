package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.model.Transaction;
import com.bank.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository tr;
	
	public Transaction save(Transaction transact) {
		return tr.save(transact);
	}
}
