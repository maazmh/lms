package com.maaz.lms.form;

import java.io.Serializable;
import java.util.List;

public class AdminForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyAccountId;
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private Integer department;
	private String emailId;
	private Integer deleted;
	private Integer admin;
	private Integer leavesAllocated;
	private Integer leavesCarriedForward;
	private List<Integer> approvers;
	
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
	public Integer getDepartment() {
		return department;
	}
	public void setDepartment(Integer department) {
		this.department = department;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Integer getAdmin() {
		return admin;
	}
	public void setAdmin(Integer admin) {
		this.admin = admin;
	}
	public List<Integer> getApprovers() {
		return approvers;
	}
	public void setApprovers(List<Integer> approvers) {
		this.approvers = approvers;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getCompanyAccountId() {
		return companyAccountId;
	}
	public void setCompanyAccountId(Integer companyAccountId) {
		this.companyAccountId = companyAccountId;
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
}
