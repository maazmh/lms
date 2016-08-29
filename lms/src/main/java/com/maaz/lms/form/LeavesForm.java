package com.maaz.lms.form;

public class LeavesForm {
	private Integer employeeId;
	private String employeeName;
	private String dtFrom;
	private String dtTo;
	private Integer leaveType;
	private String leaveReason;
	
	private Integer leavesAllocated;
	private Integer leavesUsed;
	private Integer leavesRemaining;
	private Integer sickLeavesUsed;
	private Integer unpaidLeavesUsed;
	private Integer leavesPendingApproval;
	
	
	public String getDtFrom() {
		return dtFrom;
	}
	public void setDtFrom(String dtFrom) {
		this.dtFrom = dtFrom;
	}
	public String getDtTo() {
		return dtTo;
	}
	public void setDtTo(String dtTo) {
		this.dtTo = dtTo;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getLeavesAllocated() {
		return leavesAllocated;
	}
	public void setLeavesAllocated(Integer leavesAllocated) {
		this.leavesAllocated = leavesAllocated;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Integer getLeavesUsed() {
		return leavesUsed;
	}
	public void setLeavesUsed(Integer leavesUsed) {
		this.leavesUsed = leavesUsed;
	}
	public Integer getLeavesRemaining() {
		return leavesRemaining;
	}
	public void setLeavesRemaining(Integer leavesRemaining) {
		this.leavesRemaining = leavesRemaining;
	}
	public Integer getSickLeavesUsed() {
		return sickLeavesUsed;
	}
	public void setSickLeavesUsed(Integer sickLeavesUsed) {
		this.sickLeavesUsed = sickLeavesUsed;
	}
	public Integer getUnpaidLeavesUsed() {
		return unpaidLeavesUsed;
	}
	public void setUnpaidLeavesUsed(Integer unpaidLeavesUsed) {
		this.unpaidLeavesUsed = unpaidLeavesUsed;
	}
	public Integer getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(Integer leaveType) {
		this.leaveType = leaveType;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public Integer getLeavesPendingApproval() {
		return leavesPendingApproval;
	}
	public void setLeavesPendingApproval(Integer leavesPendingApproval) {
		this.leavesPendingApproval = leavesPendingApproval;
	}
}
