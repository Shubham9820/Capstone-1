package com.bank.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}