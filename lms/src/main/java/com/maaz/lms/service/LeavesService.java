package com.maaz.lms.service;

import java.util.List;
import java.util.Map;

import com.maaz.lms.form.DashboardForm;
import com.maaz.lms.form.LeavesForm;
import com.maaz.lms.vo.LeavesByMonthVo;
import com.maaz.lms.vo.LeavesCalendarResponse;
import com.maaz.lms.vo.LeavesVo;

public interface LeavesService {
	public List<LeavesCalendarResponse> getLeavesForCalendar(Integer companyAccountId, Integer employeeId);

	public Map<Integer, String> getAllLeaveTypes();
	
	public LeavesForm getLeavesForForm(LeavesForm form, Integer employeeId, Integer year);

	public void saveLeave(LeavesForm leavesForm);

	public List<LeavesVo> getPendingLeaveApprovals(Integer approverId);

	public void approveLeave(Integer leaveId, Integer approverId, String notes);
	
	public void rejectLeave(Integer leaveId, Integer approverId, String rejectionNotes);

	public DashboardForm getDataForDashboard(DashboardForm form, Integer empId, Integer year);

	public Map<Integer, LeavesByMonthVo> getLeavesByMonth(Integer employeeid, Integer year);

}
