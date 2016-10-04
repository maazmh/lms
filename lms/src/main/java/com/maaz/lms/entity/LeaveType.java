package com.maaz.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "LeaveType")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class LeaveType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idLeaveType;
	private String leaveType;
	
	@Id
	@Column(name = "idLeaveType")
	public Integer getIdLeaveType() {
		return idLeaveType;
	}
	public void setIdLeaveType(Integer idLeaveType) {
		this.idLeaveType = idLeaveType;
	}
	
	@Column(name = "leaveType")
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
}
