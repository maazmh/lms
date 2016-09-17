package com.maaz.lms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	private String password;
//	private Integer allocatedLeaves;
//	private Integer carriedForwardLeaves;
	private boolean isAdmin;
	private boolean isDeleted;
	private Date lastLogin;
	private CompanyAccount company;
	private Color color;
	private Employee reportsTo;
	private Set<EmployeeFiscalYearLeaves> empFiscalYrLeaves = new HashSet<EmployeeFiscalYearLeaves>();
	
	private Set<Approvers> approvers = new HashSet<Approvers>();
	private Set<Leaves> leaves = new HashSet<Leaves>();
	
	private Department department;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
//	@Column(name = "allocatedLeaves")
//	public Integer getAllocatedLeaves() {
//		return allocatedLeaves;
//	}
//	public void setAllocatedLeaves(Integer allocatedLeaves) {
//		this.allocatedLeaves = allocatedLeaves;
//	}
	
	@OneToMany(fetch = FetchType.EAGER, 
			cascade=CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "employee")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OrderBy
	//@JoinTable(name = "Approvers", joinColumns = { @JoinColumn(name = "idEmployee") }, inverseJoinColumns = { @JoinColumn(name = "idApprover") })
	public Set<Approvers> getApprovers() {
		return approvers;
	}
	public void setApprovers(Set<Approvers> approvers) {
		this.approvers = approvers;
	}
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="departmentId")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@OneToMany(fetch = FetchType.EAGER, 
			cascade=CascadeType.ALL,
			mappedBy = "employee")
	@OrderBy
	public Set<Leaves> getLeaves() {
		return leaves;
	}
	public void setLeaves(Set<Leaves> leaves) {
		this.leaves = leaves;
	}
	
//	@Column(name = "carriedForwardLeaves")
//	public Integer getCarriedForwardLeaves() {
//		return carriedForwardLeaves;
//	}
//	public void setCarriedForwardLeaves(Integer carriedForwardLeaves) {
//		this.carriedForwardLeaves = carriedForwardLeaves;
//	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastLogin")
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="idCompanyAccount")
	public CompanyAccount getCompany() {
		return company;
	}
	public void setCompany(CompanyAccount company) {
		this.company = company;
	}
	
	@OneToMany(fetch = FetchType.EAGER, 
			cascade=CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "employee")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OrderBy
	public Set<EmployeeFiscalYearLeaves> getEmpFiscalYrLeaves() {
		return empFiscalYrLeaves;
	}
	public void setEmpFiscalYrLeaves(Set<EmployeeFiscalYearLeaves> empFiscalYrLeaves) {
		this.empFiscalYrLeaves = empFiscalYrLeaves;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="idColor")
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="reportsTo")
	public Employee getReportsTo() {
		return reportsTo;
	}
	public void setReportsTo(Employee reportsTo) {
		this.reportsTo = reportsTo;
	}
}
