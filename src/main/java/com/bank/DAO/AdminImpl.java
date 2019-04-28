package com.bank.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bank.model.UserAccount;
@Repository("AdminDAO")
@Transactional
public class AdminImpl implements Admin {
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Override
	public List<UserAccount> listuser() {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserAccount");
		List<UserAccount> listuser = query.list();
		return listuser;
		
			}

}
