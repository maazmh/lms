package com.maaz.lms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maaz.lms.dao.EmployeeDao;
import com.maaz.lms.dao.LeavesDao;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.entity.EmployeeFiscalYearLeaves;
import com.maaz.lms.entity.LeaveApprovals;
import com.maaz.lms.entity.Leaves;
import com.maaz.lms.util.Constants;
import com.maaz.lms.util.DateUtils;
import com.maaz.lms.vo.EmployeeLeavesReportVo;
import com.maaz.lms.vo.EmployeeLeavesVo;
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
	
	@Override
	public EmployeeLeavesReportVo searchDataForEmployeeLeavesReport(Integer companyAccountId, Integer dept, Integer year) {
		try {
			
			/*
			 * Set first and last day of year so that leaves will be recieved only for that year.
			 * */
			Calendar firstDayOfYear = Calendar.getInstance();
			firstDayOfYear.set(Calendar.YEAR, year);
			firstDayOfYear.set(Calendar.MONTH, 0);
			firstDayOfYear.set(Calendar.DAY_OF_MONTH, 01);
			Calendar lastDayOfYear = Calendar.getInstance();
			lastDayOfYear.set(Calendar.YEAR, year);
			lastDayOfYear.set(Calendar.MONTH, 11);
			lastDayOfYear.set(Calendar.DAY_OF_MONTH, 31);
			
			
			EmployeeLeavesReportVo reportVo = new EmployeeLeavesReportVo();
			List<EmployeeLeavesVo> lstEmpLeaves = new ArrayList<EmployeeLeavesVo>();
			
			List<Employee> lstEmployee = null;
			if(dept!=null) {
				lstEmployee = employeeDao.getAllEmployeesByDepartment(companyAccountId, dept);
			} else {
				lstEmployee = employeeDao.getAllEmployees(companyAccountId);
			}
			for(Employee emp : lstEmployee) {
				reportVo.setRecordsTotal(lstEmployee.size());
				
				EmployeeLeavesVo empVo = new EmployeeLeavesVo();
				empVo.setEmployeeId(emp.getIdEmployee());
				empVo.setEmployeeName(emp.getFirstName() + " " + emp.getLastName());
				empVo.setDepartment(emp.getDepartment().getDeptName());
				
				Set<EmployeeFiscalYearLeaves> setFiscalYear = emp.getEmpFiscalYrLeaves();
				Iterator<EmployeeFiscalYearLeaves> itrEmpFiscal = setFiscalYear.iterator();
				while(itrEmpFiscal.hasNext()) {
					EmployeeFiscalYearLeaves empFiscalYr = itrEmpFiscal.next();
					Calendar cal = Calendar.getInstance();
					cal.setTime(empFiscalYr.getFiscalYear().getDtTo());
					if(cal.get(Calendar.YEAR)==year) {
						empVo.setLeavesAllocated(empFiscalYr.getLeavesAllocated());
						empVo.setLeavesCarriedForward(empFiscalYr.getLeavesCarriedForward());
					}
				}
				
				
				Integer leavesUsed = 0;
				Integer leavesRemaining = empVo.getLeavesAllocated() + empVo.getLeavesCarriedForward();
				Integer sickLeavesUsed = 0;
				Integer unpaidLeavesUsed = 0;
				Integer leavesPendingApproval = 0; 
				Integer leavesRejected = 0;
				
				List<Leaves> lstLeaves = leavesDao.getLeaves(emp.getIdEmployee(), firstDayOfYear.getTime(), lastDayOfYear.getTime());
				if(lstLeaves!=null && lstLeaves.size() > 0) {
					for(Leaves leave : lstLeaves) {
						
						/*
						 * Get Difference between the days
						 * */
						int days = (int) DateUtils.dateDiffExcludeWeekends(leave.getDtFrom(), leave.getDtTo());
						
						/*
						 * Vacation
						 * */
						if(leave.getLeaveType().getIdLeaveType().equals(Constants.LEAVE_TYPE_VACATION)) {
							Boolean leaveApproved = leaveApproved(leave, emp.getApprovers().size());
							if(leaveApproved!=null && leaveApproved) {
								leavesUsed = leavesUsed + days;
								leavesRemaining = leavesRemaining - days;
							} else if(leaveApproved!=null && !leaveApproved) {
								leavesRejected = leavesRejected + days; //Leave Rejected
							} else if(leaveApproved==null) {
								leavesPendingApproval = leavesPendingApproval + days;
							}
						}
						
						/*
						 * Sick
						 * */
						if(leave.getLeaveType().getIdLeaveType().equals(Constants.LEAVE_TYPE_SICK)) {
							Boolean leaveApproved = leaveApproved(leave, emp.getApprovers().size());
							if(leaveApproved!=null && leaveApproved) {
								sickLeavesUsed = sickLeavesUsed + days;
							} else if(leaveApproved!=null && !leaveApproved) {
								leavesRejected = leavesRejected + days; //Leave Rejected
							} else if(leaveApproved==null) {
								leavesPendingApproval = leavesPendingApproval + days;
							}
						}
						
						/*
						 * Unpaid
						 * */
						if(leave.getLeaveType().getIdLeaveType().equals(Constants.LEAVE_TYPE_UNPAID)) {
							Boolean leaveApproved = leaveApproved(leave, emp.getApprovers().size());
							if(leaveApproved!=null && leaveApproved) {
								unpaidLeavesUsed = unpaidLeavesUsed + days;
							} else if(leaveApproved!=null && !leaveApproved) {
								leavesRejected = leavesRejected + days; //Leave Rejected
							} else if(leaveApproved==null) {
								leavesPendingApproval = leavesPendingApproval + days;
							}
						}
					}
				}
				
				empVo.setVacationLeaves(leavesUsed);
				empVo.setSickLeaves(sickLeavesUsed);
				empVo.setUnpaidLeaves(unpaidLeavesUsed);
				empVo.setUnApprovedLeaves(leavesPendingApproval);
				empVo.setLeavesRemaining(leavesRemaining);
				empVo.setLeavesRejected(leavesRejected);
				
				lstEmpLeaves.add(empVo);
			}
			reportVo.setData(lstEmpLeaves);
			return reportVo;
		} catch(Exception e) {
			logger.error("Exception in Service - searchDataForEmployeeLeavesReport",e);
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
