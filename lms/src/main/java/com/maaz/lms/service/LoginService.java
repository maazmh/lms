package com.maaz.lms.service;

import com.maaz.lms.entity.Employee;

public interface LoginService {
	public Employee login(String username, String password);

	public void changePassword(String username, String password);
}
