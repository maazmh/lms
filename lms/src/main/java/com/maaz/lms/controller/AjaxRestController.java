package com.maaz.lms.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maaz.lms.service.LeavesService;
import com.maaz.lms.vo.LeaveApprovalsVo;
import com.maaz.lms.vo.LeavesByMonthVo;
import com.maaz.lms.vo.LeavesCalendarResponse;
import com.maaz.lms.vo.LeavesVo;

@RestController
public class AjaxRestController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxRestController.class);
	
	@Autowired
	LeavesService leavesService;
	
	//@JsonView(Views.Public.class)
	@RequestMapping(value = "/api/getLeaves/{companyAccountId}/{employeeid}", method = RequestMethod.GET)
	public List<LeavesCalendarResponse> getLeaves(@PathVariable Integer companyAccountId, @PathVariable Integer employeeid) {
		return leavesService.getLeavesForCalendar(companyAccountId, employeeid);
	}
	
	
	@RequestMapping(value = "/api/getLeavesForMonthChart/{employeeid}/{year}", method = RequestMethod.GET, produces={"application/json"})
	public Map<Integer, LeavesByMonthVo> getLeavesByMonthForChart(@PathVariable Integer employeeid, @PathVariable Integer year) {
		
		//@ TODO : Remove Hardcodings
		year = 2016;
		employeeid = 1;
		
		logger.info("getLeavesByMonthForChart - employeeid: {}, year: {}", employeeid,year);
		
		Map<Integer, LeavesByMonthVo> mapLeavesByMonth = leavesService.getLeavesByMonth(employeeid, year);
		return mapLeavesByMonth;
	}
	
	
	/**
	 * I am not using this method. The datatable is setup in a different way here. 
	 * This API call is not needed now.
	 * */
	@RequestMapping(value = "/api/getPendingLeaveApprovals/{companyAccountId}/{approverId}", method = RequestMethod.GET, produces={"application/json"})
	public LeaveApprovalsVo getPendingLeaveApprovals(@PathVariable Integer companyAccountId, @PathVariable Integer approverId) {
		logger.info("getPendingLeaveApprovals - companyAccountId: {}, approverId: {}", companyAccountId, approverId);
		List<LeavesVo> lstLeavesVo = leavesService.getPendingLeaveApprovals(approverId);
		if(lstLeavesVo!=null && lstLeavesVo.size()>0) {
			LeaveApprovalsVo vo = new LeaveApprovalsVo();
			vo.setData(lstLeavesVo);
			vo.setRecordsTotal(lstLeavesVo.size());
			return vo;
		} else {
			return null;
		}
	}
}
