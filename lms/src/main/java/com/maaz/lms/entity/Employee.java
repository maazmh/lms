package com.maaz.lms.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idEmployee;
	private String firstName;
	private String lastName;
	private String emailId;
	private Integer allocatedLeaves;
	private boolean isAdmin;
	private boolean isDeleted;
	
	private Set<Approvers> approvers = new HashSet<Approvers>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmployee")
	public Integer getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}
	
	@Column(name = "firstName")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "lastName")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "isAdmin")
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@Column(name = "isDeleted")
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Column(name = "emailId")
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@Column(name = "allocatedLeaves")
	public Integer getAllocatedLeaves() {
		return allocatedLeaves;
	}
	public void setAllocatedLeaves(Integer allocatedLeaves) {
		this.allocatedLeaves = allocatedLeaves;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
	public Set<Approvers> getApprovers() {
		return approvers;
	}
	public void setApprovers(Set<Approvers> approvers) {
		this.approvers = approvers;
	}
}
