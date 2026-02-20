package com.spring.service;

import com.spring.model.User;

public interface IUserService {
	public void addUser(User user);
	public User checkUser(String username, String password);
}
