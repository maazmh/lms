package com.maaz.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maaz.lms.dao.LoginDao;
import com.maaz.lms.entity.Employee;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao dao;
	
	@Override
	public Employee login(String username, String password) {
		return dao.login(username, password);
	}

	@Override
	public void changePassword(String username, String password) {
		dao.changePassword(username, password);
	}

}
