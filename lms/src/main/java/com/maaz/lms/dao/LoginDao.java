package com.maaz.lms.dao;

import com.maaz.lms.entity.Employee;

public interface LoginDao {
	public Employee login(String username, String password);

	public void changePassword(String username, String password);
}
