package com.maaz.lms.form;

import java.util.List;

import com.maaz.lms.vo.LeavesVo;

public class LeavesForm {
	private Integer companyAccountId;
	private Integer employeeId;
	private String employeeName;
	private String dtFrom;
	private String dtTo;
	private Integer leaveType;
	private String leaveReason;
	private Integer year;
	private String department;
	
	private Integer leavesAllocated;
	private Integer carriedForwardLeaves;
	private Integer leavesUsed;
	private Integer leavesRemaining;
	private Integer sickLeavesUsed;
	private Integer unpaidLeavesUsed;
	private Integer leavesPendingApproval;
	
	private List<LeavesVo> lstLeaves;
	
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
	public Integer getCarriedForwardLeaves() {
		return carriedForwardLeaves;
	}
	public void setCarriedForwardLeaves(Integer carriedForwardLeaves) {
		this.carriedForwardLeaves = carriedForwardLeaves;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getCompanyAccountId() {
		return companyAccountId;
	}
	public void setCompanyAccountId(Integer companyAccountId) {
		this.companyAccountId = companyAccountId;
	}
	public List<LeavesVo> getLstLeaves() {
		return lstLeaves;
	}
	public void setLstLeaves(List<LeavesVo> lstLeaves) {
		this.lstLeaves = lstLeaves;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
}
