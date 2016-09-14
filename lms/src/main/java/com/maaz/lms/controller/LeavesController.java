package com.maaz.lms.controller;

import java.util.Calendar;
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
import com.maaz.lms.util.Constants;
import com.maaz.lms.vo.LeavesVo;

@Controller
public class LeavesController {
	private static final Logger logger = LoggerFactory.getLogger(LeavesController.class);
	
	@Autowired
	LeavesService lService;
	
	@Value("${exception.msg.standard}")
	String standardExceptionMsg;
	
	@Value("${msg.leave.approved}")
	String leaveApprovedMessage;
	
	@Value("${msg.leave.rejected}")
	String leaveRejectedMessage;
	
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView showCalendar(HttpSession session, 
			@ModelAttribute("leavesForm") LeavesForm leavesForm) {
		ModelAndView model = new ModelAndView("calendar");
		try {
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
			logger.info("Employee Id: {}", empId);
			leavesForm.setEmployeeId(empId);
			
			Integer companyAccountId = (Integer) session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID);
			logger.info("companyAccountId: {}", companyAccountId);
			leavesForm.setCompanyAccountId(companyAccountId);
			
			Map<Integer, String> mapLeaveTypes = lService.getAllLeaveTypes();
			model.addObject("leaveTypes", mapLeaveTypes);
			
			if(leavesForm.getYear()==null) {
				leavesForm.setYear(Calendar.getInstance().get(Calendar.YEAR));
			}
			logger.info("leavesForm.getYear(): {}", leavesForm.getYear());
			leavesForm = lService.getLeavesForForm(leavesForm, empId, leavesForm.getYear());
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
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
			logger.info("Employee Id: {}", empId);
			
			//@ TODO: Remove these hardcoding below
			leavesForm.setEmployeeId(1);
			leavesForm.setYear(2016);
			empId = 1;
			
			lService.saveLeave(leavesForm);
			
			Map<Integer, String> mapLeaveTypes = lService.getAllLeaveTypes();
			model.addObject("leaveTypes", mapLeaveTypes);
			
			leavesForm = lService.getLeavesForForm(leavesForm, empId, leavesForm.getYear());
			
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
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
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
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
			logger.info("Employee Id: {}", empId);
			
			//@ TODO: Remove Hardcoding
			empId = 2;
			
			Integer approverId = empId;
			lService.approveLeave(approvalForm.getLeaveId(), approverId, approvalForm.getNote());
			
			model.addObject("successMessage", leaveApprovedMessage);
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
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
			logger.info("Employee Id: {}", empId);
			
			//@ TODO: Remove Hardcoding
			empId = 2;
			
			Integer approverId = empId;
			lService.rejectLeave(approvalForm.getLeaveId(), approverId, approvalForm.getNote());
			model.addObject("successMessage", leaveRejectedMessage);
		} catch(Exception e) {
			logger.error("Exception in rejectLeave",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
}
