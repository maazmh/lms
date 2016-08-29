package com.maaz.lms.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.maaz.lms.form.LoginForm;
import com.maaz.lms.service.LoginService;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLoginPage(HttpSession session, 
			@ModelAttribute("loginForm") LoginForm loginForm) {
		ModelAndView model = new ModelAndView("login");
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpSession session, 
			@ModelAttribute("loginForm") LoginForm loginForm) {
		ModelAndView model = new ModelAndView("login");
		Integer empId = loginService.login(loginForm.getUsername(), loginForm.getPassword());
		logger.info("empId: {}", empId);
		session.setAttribute("employeeId", empId);
		return model;
	}
}
