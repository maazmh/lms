package com.maaz.lms.service;

import java.util.List;
import java.util.Map;

import com.maaz.lms.form.LeavesForm;
import com.maaz.lms.vo.LeavesCalendarResponse;

public interface LeavesService {
	public List<LeavesCalendarResponse> getLeavesForCalendar(Integer employeeId);

	public Map<Integer, String> getAllLeaveTypes();
	
	public LeavesForm getLeavesForForm(LeavesForm form, Integer employeeId);

	public void saveLeave(LeavesForm leavesForm);
}
