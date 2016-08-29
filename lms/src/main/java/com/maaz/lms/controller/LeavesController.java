package com.maaz.lms.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.maaz.lms.form.LeavesForm;
import com.maaz.lms.service.LeavesService;

@Controller
public class LeavesController {
	private static final Logger logger = LoggerFactory.getLogger(LeavesController.class);
	
	@Autowired
	LeavesService lService;
	
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView showCalendar(HttpSession session, 
			@ModelAttribute("leavesForm") LeavesForm leavesForm) {
		ModelAndView model = new ModelAndView("calendar");
		Integer empId = (Integer) session.getAttribute("employeeId");
		logger.info("Employee Id: {}", empId);
		
		//@ TODO: Remove these hardcoding below
		leavesForm.setEmployeeId(1);
		empId = 1;
		
		Map<Integer, String> mapLeaveTypes = lService.getAllLeaveTypes();
		model.addObject("leaveTypes", mapLeaveTypes);
		
		leavesForm = lService.getLeavesForForm(leavesForm, empId);
		
		return model;
	}
	
	@RequestMapping(value = "/saveLeave", method = RequestMethod.POST)
	public ModelAndView saveLeave(HttpSession session, @ModelAttribute("leavesForm") LeavesForm leavesForm) {
		ModelAndView model = new ModelAndView("calendar");
		
		Integer empId = (Integer) session.getAttribute("employeeId");
		logger.info("Employee Id: {}", empId);
		
		//@ TODO: Remove these hardcoding below
		leavesForm.setEmployeeId(1);
		empId = 1;
		
		lService.saveLeave(leavesForm);
		
		Map<Integer, String> mapLeaveTypes = lService.getAllLeaveTypes();
		model.addObject("leaveTypes", mapLeaveTypes);
		
		leavesForm = lService.getLeavesForForm(leavesForm, empId);
		
		leavesForm.setDtFrom(null);
		leavesForm.setDtTo(null);
		leavesForm.setLeaveReason(null);
		
		return model;
	}
	
}
