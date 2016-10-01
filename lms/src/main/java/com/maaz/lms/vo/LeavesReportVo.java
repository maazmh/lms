package com.maaz.lms.vo;

import java.util.List;

public class LeavesReportVo {
	private Integer draw;
	private Integer recordsTotal;
	private List<LeavesVo> data;
	
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
	public List<LeavesVo> getData() {
		return data;
	}
	public void setData(List<LeavesVo> data) {
		this.data = data;
	}
}
