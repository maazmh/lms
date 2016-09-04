package com.maaz.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maaz.lms.dao.LoginDao;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.vo.AdminEmployeeVo;
import com.maaz.lms.vo.EmployeeVo;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao dao;
	
	@Override
	public Integer login(String username, String password) {
		return dao.login(username, password);
	}

	@Override
	public AdminEmployeeVo getAllEmployees(Integer companyAccountId) {
		AdminEmployeeVo adminEmployeeVo = new AdminEmployeeVo();
		List<EmployeeVo> lst = null;
		List<Employee> lstEmp = dao.getAllEmployees(companyAccountId);
		if(lstEmp!=null) {
			lst = new ArrayList<EmployeeVo>();
			for(Employee emp : lstEmp) {
				EmployeeVo vo = new EmployeeVo();
				vo.setIdEmployee(emp.getIdEmployee());
				vo.setFirstName(emp.getFirstName());
				vo.setLastName(emp.getLastName());
				vo.setEmailId(emp.getEmailId());
				vo.setAdmin(emp.isAdmin());
				vo.setDepartment(emp.getDepartment().getDeptName());
				lst.add(vo);
			}
		}
		adminEmployeeVo.setDraw(1);
		adminEmployeeVo.setRecordsTotal(lstEmp != null ? lstEmp.size() : 0);
		adminEmployeeVo.setData(lst);
		return adminEmployeeVo;
	}

}
