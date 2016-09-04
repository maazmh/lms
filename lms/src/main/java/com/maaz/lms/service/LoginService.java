package com.maaz.lms.service;

import com.maaz.lms.vo.AdminEmployeeVo;

public interface LoginService {
	public Integer login(String username, String password);

	public AdminEmployeeVo getAllEmployees(Integer companyAccountId);
}
