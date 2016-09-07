package com.maaz.lms.controller;

import java.util.Map;

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
import com.maaz.lms.service.CommonService;
import com.maaz.lms.service.LoginService;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView showAdminPage(HttpSession session, 
			@ModelAttribute("adminForm") AdminForm form) {
		ModelAndView model = new ModelAndView("admin");
		logger.debug("showAdminPage");
		Map<Integer, String> mapDepts = commonService.getAllDepartmentsMap();
		model.addObject("mapDepts", mapDepts);
		return model;
	}
	
	@RequestMapping(value = "/save-employee", method = RequestMethod.POST)
	public ModelAndView saveEmployee(HttpSession session, @ModelAttribute("adminForm") AdminForm form) {
		ModelAndView model = new ModelAndView("redirect:/admin");
		logger.debug("save-Employee");
		commonService.saveEmployee(form);
		return model;
	}

}
