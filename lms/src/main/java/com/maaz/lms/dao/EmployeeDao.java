package com.maaz.lms.dao;

import java.util.List;

import com.maaz.lms.entity.Department;
import com.maaz.lms.entity.Employee;

public interface EmployeeDao {
	public Employee getEmployee(Integer employeeId);

	public List<Employee> getAllEmployees(Integer companyAccountId);

	public List<Department> getAllDepartments();
}
