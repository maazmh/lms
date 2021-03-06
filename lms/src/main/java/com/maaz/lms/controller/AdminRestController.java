package com.maaz.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maaz.lms.service.CommonService;
import com.maaz.lms.service.LoginService;
import com.maaz.lms.vo.AdminDepartmentVo;
import com.maaz.lms.vo.AdminEmployeeVo;

@RestController
public class AdminRestController {
	private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getAllEmployees/{companyAccountId}", method = RequestMethod.GET)
	public AdminEmployeeVo getAllEmployees(@PathVariable Integer companyAccountId) {
		logger.debug("getAllEmployees: companyAccountId: {}", companyAccountId);
		return commonService.getAllEmployees(companyAccountId);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getAllEmployeesTest/{companyAccountId}", method = RequestMethod.GET)
	public String getAllEmployeesTest(@PathVariable Integer companyAccountId) {
		logger.debug("getAllEmployeesTest: companyAccountId: {}", companyAccountId);
		String str = "{\"draw\":1,\"recordsTotal\":3,\"data\":[{\"idEmployee\":1,\"firstName\":\"Maaz\",\"lastName\":\"Hurzuk\",\"emailId\":null,\"department\":\"IT\",\"admin\":false},{\"idEmployee\":2,\"firstName\":\"Iyad\",\"lastName\":\"Farah\",\"emailId\":null,\"department\":\"IT\",\"admin\":false},{\"idEmployee\":3,\"firstName\":\"Scott\",\"lastName\":\"Weeman\",\"emailId\":null,\"department\":\"Management\",\"admin\":false},{\"idEmployee\":4,\"firstName\":\"Scott\",\"lastName\":\"Weeman\",\"emailId\":null,\"department\":\"Management\",\"admin\":false}]}";
		return str;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getAllDepartments", method = RequestMethod.GET)
	public AdminDepartmentVo getAllDepartments() {
		logger.debug("getAllDepartments");
		return commonService.getAllDepartments();
	}
}
