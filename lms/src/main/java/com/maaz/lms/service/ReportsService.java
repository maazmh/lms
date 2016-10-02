package com.maaz.lms.service;

import java.util.List;

import com.maaz.lms.vo.EmployeeLeavesReportVo;
import com.maaz.lms.vo.LeavesReportVo;

public interface ReportsService {
	public LeavesReportVo searchDataForLeavesReport(Integer companyAccountId, List<Integer> empIds,
			Integer dept, String dtFrom, String dtTo, Integer leaveType, Integer isApproved);

	public EmployeeLeavesReportVo searchDataForEmployeeLeavesReport(Integer companyAccountId, Integer dept, Integer year);
}
