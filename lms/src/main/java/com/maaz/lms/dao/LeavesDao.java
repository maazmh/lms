package com.maaz.lms.dao;

import java.util.Date;
import java.util.List;

import com.maaz.lms.entity.FiscalYear;
import com.maaz.lms.entity.LeaveApprovals;
import com.maaz.lms.entity.LeaveType;
import com.maaz.lms.entity.Leaves;

public interface LeavesDao {
	public List<Leaves> getLeaves(Integer employeeId);
	
	public List<Leaves> getLeavesByDepartment(Integer companyAccountId, Integer departmentId);

	public List<LeaveType> getAllLeaveTypes();

	public LeaveType getLeaveType(Integer idLeaveType);

	public Integer saveLeave(Leaves leave);

	public List<Leaves> getPendingLeaveApprovals(Integer approverId);

	public void saveOrUpdateLeaveApprovals(LeaveApprovals la);

	public Leaves getLeaveById(Integer leaveId);
	
	public List<Leaves> getLeaves(Integer employeeId, Date dtFrom, Date dtTo);

	public List<FiscalYear> getAllFiscalYears();

	public FiscalYear getFiscalYear(Integer fiscalYearId);
	
	public List<Leaves> searchLeaves(Integer companyAccountId, List<Integer> empIds,
			Integer dept, String dtFrom, String dtTo, Integer leaveType, Integer isApproved);
}
