package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.UserDao;
import com.spring.model.User;

@Service
@Transactional
public class UserService implements IUserService{
	
	private UserDao dao;
	
	@Autowired
	public UserService(UserDao dao) {
		this.dao = dao;
	}

	@Override
	public void addUser(User user) {
		dao.save(user);
	}

	public User checkUser(String username, String password) {
		return dao.findByUsernameAndPassword(username, password);
	}

	public User getUser(String val) {
		return dao.findByUsername(val);
	}
}
