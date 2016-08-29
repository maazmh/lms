package com.maaz.lms.dao;

import java.util.List;

import com.maaz.lms.entity.LeaveType;
import com.maaz.lms.entity.Leaves;

public interface LeavesDao {
	public List<Leaves> getLeaves(Integer employeeId);

	public List<LeaveType> getAllLeaveTypes();
}
