package com.maaz.lms.dao;

import com.maaz.lms.entity.Employee;

public interface LoginDao {
	public Integer login(String username, String password);

	public Employee getEmployee(Integer employeeId);
}
