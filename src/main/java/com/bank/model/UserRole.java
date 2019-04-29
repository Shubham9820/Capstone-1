package com.bank.model;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column(name = "user_id")
    private int userId;
    @Column(name = "role_Id")
    private int roleId;

    public UserRole(UserRole ur) {
        this.userId = ur.getUserId();
        this.roleId = ur.getRoleId();
    }
    
    public UserRole() {
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

  
}
