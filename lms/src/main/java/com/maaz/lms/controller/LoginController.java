package com.maaz.lms.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${exception.msg.standard}")
	String standardExceptionMsg;
	
	@Value("${login.failed.msg}")
	String loginFailedMessage;
	
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
		try {
			Integer empId = loginService.login(loginForm.getUsername(), loginForm.getPassword());
			if(empId!=null) {
				logger.info("empId: {}", empId);
				session.setAttribute("employeeId", empId);
				model.setViewName("redirect:/calendar");
			} else {
				model.addObject("loginFailedMessage", loginFailedMessage);
			}
		} catch(Exception e) {
			logger.error("Exception in login",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
}
