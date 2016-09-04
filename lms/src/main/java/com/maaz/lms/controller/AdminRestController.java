package com.maaz.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maaz.lms.service.LoginService;
import com.maaz.lms.vo.AdminEmployeeVo;
import com.maaz.lms.vo.EmployeeVo;

@RestController
public class AdminRestController {
	private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getAllEmployees/{companyAccountId}", method = RequestMethod.GET)
	public AdminEmployeeVo getAllEmployees(@PathVariable Integer companyAccountId) {
		return loginService.getAllEmployees(companyAccountId);
	}
}
