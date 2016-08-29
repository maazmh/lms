package com.maaz.lms.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maaz.lms.entity.Employee;
import com.maaz.lms.entity.Leaves;
import com.maaz.lms.service.LeavesService;
import com.maaz.lms.vo.LeavesCalendarResponse;

@RestController
public class AjaxRestController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxRestController.class);
	
	@Autowired
	LeavesService leavesService;
	
	//@JsonView(Views.Public.class)
	@RequestMapping(value = "/api/getLeaves/{employeeid}", method = RequestMethod.GET)
	public List<LeavesCalendarResponse> getLeaves(@PathVariable Integer employeeid) {
//		
//		List<LeavesCalendarResponse> lstLeaves = new ArrayList<LeavesCalendarResponse>();
//		
//		LeavesCalendarResponse lr1 = new LeavesCalendarResponse();
//		lr1.setId(1);
//		lr1.setName("Maaz");
//		lr1.setLocation("Vacation");
//		lr1.setStartDate("2016-01-1");
//		lr1.setEndDate("2016-01-10");
//		lstLeaves.add(lr1);
//		
//		LeavesCalendarResponse lr2 = new LeavesCalendarResponse();
//		lr2.setId(2);
//		lr2.setName("Iyad");
//		lr2.setLocation("Sick");
//		lr2.setStartDate("2016-02-16");
//		lr2.setEndDate("2016-02-20");
//		lstLeaves.add(lr2);
//		
//		LeavesCalendarResponse lr3 = new LeavesCalendarResponse();
//		lr3.setId(3);
//		lr3.setName("Iyad");
//		lr3.setLocation("Sick");
//		lr3.setStartDate("2016-3-3");
//		lr3.setEndDate("2016-3-4");
//		lstLeaves.add(lr3);
//		
//		LeavesCalendarResponse lr4 = new LeavesCalendarResponse();
//		lr4.setId(4);
//		lr4.setName("Iyad");
//		lr4.setLocation("Vacation");
//		lr4.setStartDate("2016-4-3");
//		lr4.setEndDate("2016-4-4");
//		lstLeaves.add(lr4);
//		
//		LeavesCalendarResponse lr5 = new LeavesCalendarResponse();
//		lr5.setId(5);
//		lr5.setName("Maaz");
//		lr5.setLocation("Sick");
//		lr5.setStartDate("2016-5-3");
//		lr5.setEndDate("2016-5-4");
//		lstLeaves.add(lr5);
//		
//		LeavesCalendarResponse lr6 = new LeavesCalendarResponse();
//		lr6.setId(6);
//		lr6.setName("Maaz");
//		lr6.setLocation("Sick");
//		lr6.setStartDate("2016-6-3");
//		lr6.setEndDate("2016-6-4");
//		lstLeaves.add(lr6);
//		
//		LeavesCalendarResponse lr7 = new LeavesCalendarResponse();
//		lr7.setId(7);
//		lr7.setName("Maaz");
//		lr7.setLocation("Sick");
//		lr7.setStartDate("2016-7-3");
//		lr7.setEndDate("2016-7-4");
//		lstLeaves.add(lr7);
//		
//		LeavesCalendarResponse lr8 = new LeavesCalendarResponse();
//		lr8.setId(8);
//		lr8.setName("Maaz");
//		lr8.setLocation("Sick");
//		lr8.setStartDate("2016-8-3");
//		lr8.setEndDate("2016-8-4");
//		lstLeaves.add(lr8);
//		
//		LeavesCalendarResponse lr9 = new LeavesCalendarResponse();
//		lr9.setId(9);
//		lr9.setName("Maaz");
//		lr9.setLocation("Sick");
//		lr9.setStartDate("2016-9-3");
//		lr9.setEndDate("2016-9-4");
//		lstLeaves.add(lr9);
//		
//		LeavesCalendarResponse lr10 = new LeavesCalendarResponse();
//		lr10.setId(10);
//		lr10.setName("Maaz");
//		lr10.setLocation("Sick");
//		lr10.setStartDate("2016-10-3");
//		lr10.setEndDate("2016-10-4");
//		lstLeaves.add(lr10);
//		
//		LeavesCalendarResponse lr11 = new LeavesCalendarResponse();
//		lr11.setId(11);
//		lr11.setName("Maaz");
//		lr11.setLocation("Sick");
//		lr11.setStartDate("2016-11-3");
//		lr11.setEndDate("2016-11-4");
//		lstLeaves.add(lr11);
//		
//		LeavesCalendarResponse lr12 = new LeavesCalendarResponse();
//		lr12.setId(12);
//		lr12.setName("Maaz");
//		lr12.setLocation("Sick");
//		lr12.setStartDate("2016-12-3");
//		lr12.setEndDate("2016-12-4");
//		lstLeaves.add(lr12);
//		
//		LeavesCalendarResponse lr13 = new LeavesCalendarResponse();
//		lr13.setId(13);
//		lr13.setName("Maaz");
//		lr13.setLocation("Sick");
//		lr13.setStartDate("2016-01-3");
//		lr13.setEndDate("2016-01-10");
//		lstLeaves.add(lr13);
//		
		return leavesService.getLeavesForCalendar(employeeid);
	}
}
