package com.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.Balance;

public interface BalanceRepository extends JpaRepository<Balance, String> {
	Optional<Balance> findByAccountNumber(String accountNumber);
}
