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

import com.maaz.lms.form.AdminForm;
import com.maaz.lms.form.LoginForm;
import com.maaz.lms.service.LoginService;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView showAdminPage(HttpSession session, 
			@ModelAttribute("adminForm") AdminForm form) {
		ModelAndView model = new ModelAndView("admin");
		return model;
	}

}
