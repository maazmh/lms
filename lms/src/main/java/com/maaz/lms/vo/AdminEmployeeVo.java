package com.maaz.lms.vo;

import java.util.List;

public class AdminEmployeeVo {
	private Integer draw;
	private Integer recordsTotal;
	private List<EmployeeVo> data;
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public List<EmployeeVo> getData() {
		return data;
	}
	public void setData(List<EmployeeVo> data) {
		this.data = data;
	}
}
