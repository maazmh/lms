package com.maaz.lms.form;

import java.util.List;

import com.maaz.lms.vo.LeavesVo;

public class ApprovalForm {
	private List<LeavesVo> leaves;
	private Integer leaveId;
	private String note;

	public List<LeavesVo> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<LeavesVo> leaves) {
		this.leaves = leaves;
	}

	public Integer getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
