package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.EmployeeAccount;


public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, String>{
	EmployeeAccount findFirstByEmployeeId(String id);
}
