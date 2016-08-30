package com.maaz.lms.vo;

import java.io.Serializable;

public class LeavesVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idLeave;
	private Integer employeeId;
	private String employeeName;
	private String dtAppliedOn;
	private String dtFrom;
	private String dtTo;
	private String leaveType;
	private String leaveDescription;
	
	public Integer getIdLeave() {
		return idLeave;
	}
	public void setIdLeave(Integer idLeave) {
		this.idLeave = idLeave;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDtAppliedOn() {
		return dtAppliedOn;
	}
	public void setDtAppliedOn(String dtAppliedOn) {
		this.dtAppliedOn = dtAppliedOn;
	}
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
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getLeaveDescription() {
		return leaveDescription;
	}
	public void setLeaveDescription(String leaveDescription) {
		this.leaveDescription = leaveDescription;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	
}
