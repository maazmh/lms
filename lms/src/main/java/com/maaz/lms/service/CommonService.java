package com.maaz.lms.service;

import java.util.Map;

import com.maaz.lms.form.AdminForm;
import com.maaz.lms.vo.AdminDepartmentVo;
import com.maaz.lms.vo.AdminEmployeeVo;

public interface CommonService {
	public AdminEmployeeVo getAllEmployees(Integer companyAccountId);

	public AdminDepartmentVo getAllDepartments();

	public Map<Integer, String> getAllDepartmentsMap();

	public void saveEmployee(AdminForm form);

	public Map<Integer, String> getEmployeesForDropDown(Integer companyAccountId);
}
