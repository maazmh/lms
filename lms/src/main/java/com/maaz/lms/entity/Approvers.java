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

@Entity
@Table(name = "Approvers")
public class Approvers implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idApprovers;
	private Employee employee;
	private Employee approver;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idApprovers")
	public Integer getIdApprovers() {
		return idApprovers;
	}
	public void setIdApprovers(Integer idApprovers) {
		this.idApprovers = idApprovers;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idEmployee")
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idApprover")
	public Employee getApprover() {
		return approver;
	}
	public void setApprover(Employee approver) {
		this.approver = approver;
	}
}
