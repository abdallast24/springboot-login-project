package com.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.User;

public interface UserDao extends JpaRepository<User, Integer>{
	
	public User findByUsernameAndPassword(String username,String password);

	public User findByUsername(String val);

}
