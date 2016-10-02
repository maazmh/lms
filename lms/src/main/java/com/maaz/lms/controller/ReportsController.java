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
import com.maaz.lms.form.ReportForm;
import com.maaz.lms.service.CommonService;
import com.maaz.lms.service.LeavesService;
import com.maaz.lms.util.Constants;
import com.maaz.lms.vo.LeavesVo;

@Controller
public class ReportsController {
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Autowired
	LeavesService lService;
	
	@Autowired
	CommonService commonService;
	
	@Value("${exception.msg.standard}")
	String standardExceptionMsg;
	
	@Value("${msg.leave.approved}")
	String leaveApprovedMessage;
	
	@Value("${msg.leave.rejected}")
	String leaveRejectedMessage;
	
	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public ModelAndView showReportScreen(HttpSession session, 
			@ModelAttribute("reportForm") ReportForm reportForm) {
		ModelAndView model = new ModelAndView("leavesReport");
		try {
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
			logger.info("Employee Id: {}", empId);
			reportForm.setEmployeeId(empId);
			
			Integer companyAccountId = (Integer) session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID);
			logger.info("companyAccountId: {}", companyAccountId);
			reportForm.setCompanyAccountId(companyAccountId);
			
			Map<Integer, String> mapDepts = commonService.getAllDepartmentsMap();
			model.addObject("mapDepts", mapDepts);
			
			Map<Integer, String> mapLeaveTypes = lService.getAllLeaveTypes();
			model.addObject("leaveTypes", mapLeaveTypes);
			
			Map<Integer, String> mapEmployees = commonService.getEmployeesForDropDown(companyAccountId);
			model.addObject("mapEmployees", mapEmployees);
			
		} catch(Exception e) {
			logger.error("Exception in showReportScreen",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
	
	@RequestMapping(value = "/staffLeavesReport", method = RequestMethod.GET)
	public ModelAndView showEmployeeLeavesReportScreen(HttpSession session, 
			@ModelAttribute("reportForm") ReportForm reportForm) {
		ModelAndView model = new ModelAndView("staffLeavesReport");
		try {
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
			logger.info("Employee Id: {}", empId);
			reportForm.setEmployeeId(empId);
			
			Integer companyAccountId = (Integer) session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID);
			logger.info("companyAccountId: {}", companyAccountId);
			reportForm.setCompanyAccountId(companyAccountId);
			
			Map<Integer, String> mapDepts = commonService.getAllDepartmentsMap();
			model.addObject("mapDepts", mapDepts);
			
		} catch(Exception e) {
			logger.error("Exception in showEmployeeLeavesReportScreen",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
}
