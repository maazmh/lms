package com.maaz.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "CompanyAccount")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class CompanyAccount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idCompanyAccount;
	private String companyName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCompanyAccount")
	public Integer getIdCompanyAccount() {
		return idCompanyAccount;
	}
	public void setIdCompanyAccount(Integer idCompanyAccount) {
		this.idCompanyAccount = idCompanyAccount;
	}
	
	@Column(name = "companyName")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
