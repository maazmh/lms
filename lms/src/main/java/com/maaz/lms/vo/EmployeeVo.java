package com.maaz.lms.vo;

import java.util.List;

public class EmployeeVo {
	private Integer idEmployee;
	private String firstName;
	private String lastName;
	private String emailId;
	private boolean isAdmin;
	private boolean isDeleted;
	private Integer departmentId;
	private String departmentName;
	private Integer leavesAllocated;
	private Integer leavesCarriedForward;
	private List<ApproverVo> approvers;
	private String password;
	private Integer reportsTo;
	
	public Integer getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public List<ApproverVo> getApprovers() {
		return approvers;
	}
	public void setApprovers(List<ApproverVo> approvers) {
		this.approvers = approvers;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getReportsTo() {
		return reportsTo;
	}
	public void setReportsTo(Integer reportsTo) {
		this.reportsTo = reportsTo;
	}
	
}
