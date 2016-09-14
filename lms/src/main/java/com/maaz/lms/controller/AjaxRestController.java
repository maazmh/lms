package com.maaz.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maaz.lms.service.LeavesService;
import com.maaz.lms.vo.LeavesCalendarResponse;

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
}
