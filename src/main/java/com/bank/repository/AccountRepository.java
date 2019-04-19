package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.UserAccount;


public interface AccountRepository extends JpaRepository<UserAccount, String> {
	  UserAccount findFirstByUserId(int id);
}
