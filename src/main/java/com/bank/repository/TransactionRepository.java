package com.bank.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByStatus(String status);
	List<Transaction> findByFromOrTo(String from, String to);
	

}