package com.maaz.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maaz.lms.service.CommonService;
import com.maaz.lms.service.LoginService;
import com.maaz.lms.vo.AdminDepartmentVo;
import com.maaz.lms.vo.AdminEmployeeVo;
import com.maaz.lms.vo.LeavesVo;

@RestController
public class ReportRestController {
	private static final Logger logger = LoggerFactory.getLogger(ReportRestController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/search", method = RequestMethod.GET)
	public List<LeavesVo> search(@RequestParam(value="companyAccountId", required=true) Integer companyAccountId, 
			@RequestParam(value="empIds", required=false) List<Integer> empIds,
			@RequestParam(value="dept", required=false) Integer dept,
			@RequestParam(value="dtFrom", required=false) String dtFrom,
			@RequestParam(value="dtTo", required=false) String dtTo,
			@RequestParam(value="leaveType", required=false) Integer leaveType,
			@RequestParam(value="isApproved", required=false) Integer isApproved) {
		logger.debug("search: companyAccountId: {}, dept: {}, dtFrom: {}, dtTo: {}, leaveType: {}, isApproved: {}", 
				new Object[] {companyAccountId, 
						empIds!=null ? empIds.toArray() : null, dept, dtFrom, dtTo, leaveType, isApproved});
		List<LeavesVo> lst = null;
		return lst;
	}
}
