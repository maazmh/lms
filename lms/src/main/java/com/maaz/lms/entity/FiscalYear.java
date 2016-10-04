package com.maaz.lms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "FiscalYear")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class FiscalYear implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idFiscalYear;
	private Date dtFrom;
	private Date dtTo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFiscalYear")
	public Integer getIdFiscalYear() {
		return idFiscalYear;
	}
	public void setIdFiscalYear(Integer idFiscalYear) {
		this.idFiscalYear = idFiscalYear;
	}
	
	@Column(name = "dtFrom")
	public Date getDtFrom() {
		return dtFrom;
	}
	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}
	
	@Column(name = "dtTo")
	public Date getDtTo() {
		return dtTo;
	}
	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}
}
