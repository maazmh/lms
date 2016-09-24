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
import com.maaz.lms.form.DashboardForm;
import com.maaz.lms.form.LeavesForm;
import com.maaz.lms.service.LeavesService;
import com.maaz.lms.util.Constants;
import com.maaz.lms.vo.LeavesVo;

@Controller
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	LeavesService lService;
	
	@Value("${exception.msg.standard}")
	String standardExceptionMsg;
	
	@Value("${msg.leave.approved}")
	String leaveApprovedMessage;
	
	@Value("${msg.leave.rejected}")
	String leaveRejectedMessage;
	
	@Autowired
	LeavesService leavesService;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView showDashboard(HttpSession session, 
			@ModelAttribute("dashboardForm") DashboardForm form) {
		ModelAndView model = new ModelAndView("dashboard");
		try {
			Integer empId = (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID);
			logger.info("Employee Id: {}", empId);
			form.setEmployeeId(empId);
			
			Integer companyAccountId = (Integer) session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID);
			logger.info("companyAccountId: {}", companyAccountId);
			form.setCompanyAccountId(companyAccountId);
			
			Map<Integer, String> mapLeaveTypes = lService.getAllLeaveTypes();
			model.addObject("leaveTypes", mapLeaveTypes);
			
			if(form.getYear()==null) {
				form.setYear(Calendar.getInstance().get(Calendar.YEAR));
			}
			
//			form = leavesService.getDataForDashboard(form, empId, form.getYear());
//			
//			model.addObject("leavesChartData", form.getLstLeaves());
			
		} catch(Exception e) {
			logger.error("Exception in showDashboard",e);
			model.addObject("exceptionMessage", standardExceptionMsg);
		}
		return model;
	}
	
	
}
