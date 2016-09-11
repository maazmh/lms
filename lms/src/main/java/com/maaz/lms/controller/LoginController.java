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

import com.maaz.lms.entity.Employee;
import com.maaz.lms.form.LoginForm;
import com.maaz.lms.service.CommonService;
import com.maaz.lms.service.LoginService;
import com.maaz.lms.util.Constants;
import com.maaz.lms.vo.EmployeeVo;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CommonService commonService;
	
	@Value("${exception.msg.standard}")
	String standardExceptionMsg;
	
	@Value("${login.failed.msg}")
	String loginFailedMessage;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLoginPage(HttpSession session, 
			@ModelAttribute("loginForm") LoginForm loginForm) {
		ModelAndView model = new ModelAndView("login");
		session.setAttribute(Constants.SESSION_STR_EMP_ID, null);
		session.setAttribute(Constants.SESSION_STR_COMP_ACT_ID, null);
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpSession session, 
			@ModelAttribute("loginForm") LoginForm loginForm) {
		ModelAndView model = new ModelAndView("login");
		try {
			Employee emp = loginService.login(loginForm.getUsername(), loginForm.getPassword());
			if(emp!=null) {
				logger.info("empId: {}", emp.getIdEmployee());
				session.setAttribute(Constants.SESSION_STR_EMP_ID, emp.getIdEmployee());
				session.setAttribute(Constants.SESSION_STR_COMP_ACT_ID, emp.getCompany().getIdCompanyAccount());
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
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView showAccountPage(HttpSession session, 
			@ModelAttribute("loginForm") LoginForm loginForm) {
		ModelAndView model = new ModelAndView("account");
		
		Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
		logger.info("Employee Id: {}", empId);
		
		Integer companyId = (Integer) session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID);
		logger.info("company Id: {}", companyId);
		
		//@ TODO: remove hardcoding for company account
		companyId = 1;
		empId = 1;
		
		EmployeeVo vo = commonService.getEmployee(companyId, empId);
		loginForm.setUsername(vo.getEmailId());
		//loginForm.setPassword(vo.getPassword());
		return model;
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public ModelAndView changePassword(HttpSession session, 
			@ModelAttribute("loginForm") LoginForm loginForm) {
		ModelAndView model = new ModelAndView("account");
		loginService.changePassword(loginForm.getUsername(), loginForm.getPassword());
		loginForm.setUsername(null);
		loginForm.setPassword(null);
		
		Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
		logger.info("Employee Id: {}", empId);
		
		Integer companyId = (Integer) session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID);
		logger.info("company Id: {}", companyId);
		
		//@ TODO: remove hardcoding for company account
		companyId = 1;
		empId = 1;
		EmployeeVo vo = commonService.getEmployee(companyId, empId);
		loginForm.setUsername(vo.getEmailId());
		
		model.addObject("successMessage", "Password Changed Successfully");
		return model;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session, 
			@ModelAttribute("loginForm") LoginForm loginForm) {
		ModelAndView model = new ModelAndView("login");
		session.setAttribute(Constants.SESSION_STR_EMP_ID, null);
		session.setAttribute(Constants.SESSION_STR_COMP_ACT_ID, null);
		return model;
	}
}
