package com.maaz.lms.controller;

import java.util.List;
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

import com.maaz.lms.form.ApprovalForm;
import com.maaz.lms.form.LeavesForm;
import com.maaz.lms.service.LeavesService;
import com.maaz.lms.vo.LeavesVo;

@Controller
public class LeavesController {
	private static final Logger logger = LoggerFactory.getLogger(LeavesController.class);
	
	@Autowired
	LeavesService lService;
	
	@Value("${exception.msg.standard}")
	String standardExceptionMsg;
	
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView showCalendar(HttpSession session, 
			@ModelAttribute("leavesForm") LeavesForm leavesForm) {
		ModelAndView model = new ModelAndView("calendar");
		try {
			Integer empId = (Integer) session.getAttribute("employeeId");
			logger.info("Employee Id: {}", empId);
			
			//@ TODO: Remove these hardcoding below
			leavesForm.setEmployeeId(1);
			empId = 1;
			
			Map<Integer, String> mapLeaveTypes = lService.getAllLeaveTypes();
			model.addObject("leaveTypes", mapLeaveTypes);
			
			leavesForm = lService.getLeavesForForm(leavesForm, empId);
		} catch(Exception e) {
			logger.error("Exception in showCalendar",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
	@RequestMapping(value = "/saveLeave", method = RequestMethod.POST)
	public ModelAndView saveLeave(HttpSession session, @ModelAttribute("leavesForm") LeavesForm leavesForm) {
		ModelAndView model = new ModelAndView("calendar");
		try {
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
		} catch(Exception e) {
			logger.error("Exception in saveLeave",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
	@RequestMapping(value = "/approval", method = RequestMethod.GET)
	public ModelAndView showApprovalScreen(HttpSession session, @ModelAttribute("approvalForm") ApprovalForm approvalForm) {
		ModelAndView model = new ModelAndView("approval");
		try {
			Integer empId = (Integer) session.getAttribute("employeeId");
			logger.info("Employee Id: {}", empId);
			
			//@ TODO: Remove Hardcoding
			empId = 2;
			
			Integer approverId = empId;
			List<LeavesVo> lstLeaves = lService.getPendingLeaveApprovals(approverId);
			
			approvalForm.setLeaves(lstLeaves);
			
		} catch(Exception e) {
			logger.error("Exception in saveLeave",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public ModelAndView approveLeave(HttpSession session, 
			@ModelAttribute("approvalForm") ApprovalForm approvalForm) {
		ModelAndView model = new ModelAndView("redirect:/approval");
		try {
			Integer empId = (Integer) session.getAttribute("employeeId");
			logger.info("Employee Id: {}", empId);
			
			//@ TODO: Remove Hardcoding
			empId = 2;
			
			Integer approverId = empId;
			lService.approveLeave(approvalForm.getLeaveId(), approverId);
			
		} catch(Exception e) {
			logger.error("Exception in approveLeave",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public ModelAndView rejectLeave(HttpSession session, 
			@ModelAttribute("approvalForm") ApprovalForm approvalForm) {
		ModelAndView model = new ModelAndView("redirect:/approval");
		try {
			Integer empId = (Integer) session.getAttribute("employeeId");
			logger.info("Employee Id: {}", empId);
			
			//@ TODO: Remove Hardcoding
			empId = 2;
			
			Integer approverId = empId;
			lService.rejectLeave(approvalForm.getLeaveId(), approverId);
			
		} catch(Exception e) {
			logger.error("Exception in rejectLeave",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
}
