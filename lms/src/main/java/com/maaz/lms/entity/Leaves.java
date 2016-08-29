package com.maaz.lms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Leaves")
public class Leaves implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idLeaves;
	private Employee employee;
	private Date dtFrom;
	private Date dtTo;
	private Date dtAppliedOn;
	private LeaveType leaveType;
	private String leaveDescription;
	private boolean isDeleted;
	
	private Set<LeaveApprovals> leaveApprovals = new HashSet<LeaveApprovals>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idLeaves")
	public Integer getIdLeaves() {
		return idLeaves;
	}
	public void setIdLeaves(Integer idLeaves) {
		this.idLeaves = idLeaves;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtFrom")
	public Date getDtFrom() {
		return dtFrom;
	}
	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtTo")
	public Date getDtTo() {
		return dtTo;
	}
	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="employee_id")
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtAppliedOn")
	public Date getDtAppliedOn() {
		return dtAppliedOn;
	}
	public void setDtAppliedOn(Date dtAppliedOn) {
		this.dtAppliedOn = dtAppliedOn;
	}
	
	@Column(name = "isDeleted")
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="leaveType")
	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
	
	@Column(name = "leaveDescription")
	public String getLeaveDescription() {
		return leaveDescription;
	}
	public void setLeaveDescription(String leaveDescription) {
		this.leaveDescription = leaveDescription;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "leave")
	@OrderBy
	public Set<LeaveApprovals> getLeaveApprovals() {
		return leaveApprovals;
	}
	public void setLeaveApprovals(Set<LeaveApprovals> leaveApprovals) {
		this.leaveApprovals = leaveApprovals;
	}
	
	

}
