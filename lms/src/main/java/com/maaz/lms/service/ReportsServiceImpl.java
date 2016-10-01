package com.maaz.lms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maaz.lms.dao.EmployeeDao;
import com.maaz.lms.dao.LeavesDao;
import com.maaz.lms.entity.LeaveApprovals;
import com.maaz.lms.entity.Leaves;
import com.maaz.lms.vo.LeavesReportVo;
import com.maaz.lms.vo.LeavesVo;

@Service
public class ReportsServiceImpl implements ReportsService {

	private static final Logger logger = LoggerFactory.getLogger(ReportsServiceImpl.class);
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	LeavesDao leavesDao;
	
	DateFormat dfDbToStr = new SimpleDateFormat("yyyy-MM-dd"); 
	DateFormat dfStrToDb = new SimpleDateFormat("dd-MM-yyyy"); 

	@Override
	public LeavesReportVo searchDataForLeavesReport(Integer companyAccountId, List<Integer> empIds, Integer dept,
			String dtFrom, String dtTo, Integer leaveType, Integer isApproved) {
		LeavesReportVo leavesReportVo = null;
		try {
			List<Leaves> lstLeaves = leavesDao.searchLeaves(companyAccountId, empIds, dept, dtFrom, dtTo, leaveType, isApproved);
			if(lstLeaves!=null && lstLeaves.size()>0) {
				leavesReportVo = new LeavesReportVo();
				List<LeavesVo> lstLeavesVo = new ArrayList<LeavesVo>();
				for(Leaves leave : lstLeaves) {
					LeavesVo leavesVo = new LeavesVo();
					leavesVo.setIdLeave(leave.getIdLeaves());
					leavesVo.setDtFrom(dfStrToDb.format(leave.getDtFrom()));
					leavesVo.setDtTo(dfStrToDb.format(leave.getDtTo()));
					leavesVo.setDtAppliedOn(dfStrToDb.format(leave.getDtAppliedOn()));
					leavesVo.setEmployeeId(leave.getEmployee().getIdEmployee());
					leavesVo.setEmployeeName(leave.getEmployee().getFirstName() + " " + leave.getEmployee().getLastName());
					leavesVo.setDepartment(leave.getEmployee().getDepartment().getDeptName());
					
					Boolean leaveApproved = leaveApproved(leave, leave.getEmployee().getApprovers().size());
					leavesVo.setIsApproved(leaveApproved);
					
					lstLeavesVo.add(leavesVo);
				}
				leavesReportVo.setRecordsTotal(lstLeavesVo.size());
				leavesReportVo.setData(lstLeavesVo);
				return leavesReportVo;
			}
		} catch(Exception e) {
			logger.error("Exception searchDataForLeavesReport",e);
		}
		return null;
	}
	
	
	private Boolean leaveApproved(Leaves leave, int numberOfApprovers) {
		/*
		 * Get Approvals for every leave applied. 
		 * If leave is approved by all approvers: leavesUsed = leavesUsed + days; && leavesRemaining = allocatedLeaves-leavesUsed;
		 * If leave not approved by even one approver - leavesPendingApproval + days;
		 * 
		 * */
		Boolean leaveApproved = null;
		Set<LeaveApprovals> leaveApprovals = leave.getLeaveApprovals();
		if(leaveApprovals!=null) {
			int approvals = 0;
			Iterator<LeaveApprovals> itr = leaveApprovals.iterator();
			while(itr.hasNext()) {
				LeaveApprovals approval = itr.next();
				if(!approval.getIsApproved()) {
					leaveApproved = false;
				} else if(approval.getIsApproved()) {
					approvals++;
				}
			}
			/*
			 * If "numberOfApprovers" (for this employee). Say 2 have approved it "approvals" (numberOfApprovers=approvals)
			 * */
			if(numberOfApprovers==approvals) {
				leaveApproved = true;
			}
		}
		/*
		* There could be 3 conditions here.
		* 1. Leave Approved: leaveApproved = true;
		* 2. Leave Rejected: leaveApproved = false;
		* 3. Pending Approval: leaveApproved = null;
		*/
		return leaveApproved;
	}

}
