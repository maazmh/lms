package com.maaz.lms.service;

import java.util.List;
import java.util.Map;

import com.maaz.lms.form.AdminForm;
import com.maaz.lms.vo.AdminDepartmentVo;
import com.maaz.lms.vo.AdminEmployeeVo;
import com.maaz.lms.vo.EmployeeVo;
import com.maaz.lms.vo.LeavesReportVo;

public interface ReportsService {
	public LeavesReportVo searchDataForLeavesReport(Integer companyAccountId, List<Integer> empIds,
			Integer dept, String dtFrom, String dtTo, Integer leaveType, Integer isApproved);
}
