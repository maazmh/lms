package com.maaz.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "EmployeeFiscalYearLeaves")
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
public class EmployeeFiscalYearLeaves implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idEmployeeFiscalYearLeaves;
	private Employee employee;
	private FiscalYear fiscalYear;
	private Integer leavesAllocated;
	private Integer leavesCarriedForward;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idEmployeeFiscalYearLeaves")
	public Integer getIdEmployeeFiscalYearLeaves() {
		return idEmployeeFiscalYearLeaves;
	}
	public void setIdEmployeeFiscalYearLeaves(Integer idEmployeeFiscalYearLeaves) {
		this.idEmployeeFiscalYearLeaves = idEmployeeFiscalYearLeaves;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="employeeId")
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fiscalYearId")
	public FiscalYear getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(FiscalYear fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	@Column(name = "leavesAllocated")
	public Integer getLeavesAllocated() {
		return leavesAllocated;
	}
	public void setLeavesAllocated(Integer leavesAllocated) {
		this.leavesAllocated = leavesAllocated;
	}
	
	@Column(name = "leavesCarriedForward")
	public Integer getLeavesCarriedForward() {
		return leavesCarriedForward;
	}
	public void setLeavesCarriedForward(Integer leavesCarriedForward) {
		this.leavesCarriedForward = leavesCarriedForward;
	}
}
