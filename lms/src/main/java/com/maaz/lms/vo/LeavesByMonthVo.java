package com.maaz.lms.vo;

public class LeavesByMonthVo {
	private String month;
	private Integer monthId;
	private Integer noOfVacations;
	private Integer noOfSickDays;
	private Integer noOfUnpaidLeaves;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getMonthId() {
		return monthId;
	}
	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	public Integer getNoOfVacations() {
		return noOfVacations;
	}
	public void setNoOfVacations(Integer noOfVacations) {
		this.noOfVacations = noOfVacations;
	}
	public Integer getNoOfSickDays() {
		return noOfSickDays;
	}
	public void setNoOfSickDays(Integer noOfSickDays) {
		this.noOfSickDays = noOfSickDays;
	}
	public Integer getNoOfUnpaidLeaves() {
		return noOfUnpaidLeaves;
	}
	public void setNoOfUnpaidLeaves(Integer noOfUnpaidLeaves) {
		this.noOfUnpaidLeaves = noOfUnpaidLeaves;
	}
}
