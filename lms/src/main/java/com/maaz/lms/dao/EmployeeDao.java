package com.maaz.lms.dao;

import java.util.List;

import com.maaz.lms.entity.Approvers;
import com.maaz.lms.entity.CompanyAccount;
import com.maaz.lms.entity.Department;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.form.AdminForm;

public interface EmployeeDao {
	public Employee getEmployee(Integer employeeId);

	public List<Employee> getAllEmployees(Integer companyAccountId);

	public List<Department> getAllDepartments();

	public void saveEmployee(Employee emp);
	
	public void updateEmployee(Employee emp);
	
	public void deleteApprovers(Approvers approver);

	public Department getDepartment(Integer departmentId);

	public CompanyAccount getCompany(Integer companyAccountId);
}
