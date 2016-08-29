package com.maaz.lms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LeaveApprovals")
public class LeaveApprovals implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idLeaveApprovals;
	private Leaves leave;
	private Employee approver;
	private Boolean isApproved;
	private Date dtUpdated;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLeaveApprovals")
	public Integer getIdLeaveApprovals() {
		return idLeaveApprovals;
	}
	public void setIdLeaveApprovals(Integer idLeaveApprovals) {
		this.idLeaveApprovals = idLeaveApprovals;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="leaveId")
	public Leaves getLeave() {
		return leave;
	}
	public void setLeave(Leaves leave) {
		this.leave = leave;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="approverId")
	public Employee getApprover() {
		return approver;
	}
	public void setApprover(Employee approver) {
		this.approver = approver;
	}
	
	@Column(name="isApproved")
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtUpdated")
	public Date getDtUpdated() {
		return dtUpdated;
	}
	public void setDtUpdated(Date dtUpdated) {
		this.dtUpdated = dtUpdated;
	}
}
