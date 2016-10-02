package com.maaz.lms.vo;

import java.io.Serializable;

public class EmployeeLeavesVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer employeeId;
	private String employeeName;
	private String department;
	private Integer leavesAllocated;
	private Integer leavesCarriedForward;
	private Integer vacationLeaves;
	private Integer sickLeaves;
	private Integer unpaidLeaves;
	private Integer unApprovedLeaves;
	private Integer leavesRemaining;
	private Integer leavesRejected;
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getLeavesAllocated() {
		return leavesAllocated;
	}
	public void setLeavesAllocated(Integer leavesAllocated) {
		this.leavesAllocated = leavesAllocated;
	}
	public Integer getLeavesCarriedForward() {
		return leavesCarriedForward;
	}
	public void setLeavesCarriedForward(Integer leavesCarriedForward) {
		this.leavesCarriedForward = leavesCarriedForward;
	}
	public Integer getVacationLeaves() {
		return vacationLeaves;
	}
	public void setVacationLeaves(Integer vacationLeaves) {
		this.vacationLeaves = vacationLeaves;
	}
	public Integer getSickLeaves() {
		return sickLeaves;
	}
	public void setSickLeaves(Integer sickLeaves) {
		this.sickLeaves = sickLeaves;
	}
	public Integer getUnpaidLeaves() {
		return unpaidLeaves;
	}
	public void setUnpaidLeaves(Integer unpaidLeaves) {
		this.unpaidLeaves = unpaidLeaves;
	}
	public Integer getUnApprovedLeaves() {
		return unApprovedLeaves;
	}
	public void setUnApprovedLeaves(Integer unApprovedLeaves) {
		this.unApprovedLeaves = unApprovedLeaves;
	}
	public Integer getLeavesRemaining() {
		return leavesRemaining;
	}
	public void setLeavesRemaining(Integer leavesRemaining) {
		this.leavesRemaining = leavesRemaining;
	}
	public Integer getLeavesRejected() {
		return leavesRejected;
	}
	public void setLeavesRejected(Integer leavesRejected) {
		this.leavesRejected = leavesRejected;
	}

}
