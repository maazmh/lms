package com.maaz.lms.service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maaz.lms.dao.LeavesDao;
import com.maaz.lms.dao.LoginDao;
import com.maaz.lms.entity.Approvers;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.entity.LeaveApprovals;
import com.maaz.lms.entity.LeaveType;
import com.maaz.lms.entity.Leaves;
import com.maaz.lms.form.LeavesForm;
import com.maaz.lms.util.Constants;
import com.maaz.lms.util.DateUtils;
import com.maaz.lms.vo.LeavesCalendarResponse;
import com.maaz.lms.vo.LeavesVo;

@Service
public class LeavesServiceImpl implements LeavesService {
	
	private static final Logger logger = LoggerFactory.getLogger(LeavesServiceImpl.class);
	
	@Autowired
	LeavesDao leavesDao;
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	EmailService emailService;
	
	@Value("${mail.first.approval.subject}")
	String emailSubject;
	
	@Value("${mail.first.approval.body}")
	String emailBody;
	
	@Value("${mail.leave.rejection.subject}")
	String leaveRejectionEmailSubject;
	
	@Value("${mail.leave.rejection.body}")
	String leaveRejectionEmailBody;
	
	
	DateFormat dfDbToStr = new SimpleDateFormat("yyyy-MM-dd"); 
	DateFormat dfStrToDb = new SimpleDateFormat("dd-MM-yyyy"); 
	
	@Override
	public List<LeavesCalendarResponse> getLeavesForCalendar(Integer employeeId) {
		List<LeavesCalendarResponse> lstLeavesResponse = null;
		try {
			List<Leaves> lstLeaves = leavesDao.getLeaves(employeeId);
			if(lstLeaves!=null) {
				lstLeavesResponse = new ArrayList<LeavesCalendarResponse>();
				for(Leaves leaves : lstLeaves) {
					LeavesCalendarResponse resp = new LeavesCalendarResponse();
					resp.setId(leaves.getIdLeaves());
					resp.setName(leaves.getEmployee().getFirstName() + " " + leaves.getEmployee().getLastName());
					resp.setLocation(leaves.getLeaveType().getLeaveType());
					resp.setStartDate(dfDbToStr.format(leaves.getDtFrom()));
					resp.setEndDate(dfDbToStr.format(leaves.getDtTo()));
					lstLeavesResponse.add(resp);
				}
			}
		} catch(Exception e) {
			logger.error("Exception in service - getLeavesForCalendar",e);
		}
		return lstLeavesResponse;
	}

	@Override
	public Map<Integer, String> getAllLeaveTypes() {
		Map<Integer, String> mapLeaveTypes=null;
		try {
			List<LeaveType> lstLt = leavesDao.getAllLeaveTypes();
			if(lstLt!=null) {
				mapLeaveTypes = new HashMap<Integer, String>();
				for(LeaveType lt : lstLt) {
					mapLeaveTypes.put(lt.getIdLeaveType(), lt.getLeaveType());
				}
			}
		} catch(Exception e) {
			logger.error("Exception in service - getAllLeaveTypes",e);
		}
		return mapLeaveTypes;
	}

	@Override
	public LeavesForm getLeavesForForm(LeavesForm form, Integer employeeId, Integer year) {
		try {
			/*
			 * Set first and last day of year so that leaves will be recieved only for that year.
			 * */
			Calendar firstDayOfYear = Calendar.getInstance();
			firstDayOfYear.set(Calendar.YEAR, year);
			firstDayOfYear.set(Calendar.MONTH, 01);
			firstDayOfYear.set(Calendar.DAY_OF_MONTH, 01);
			Calendar lastDayOfYear = Calendar.getInstance();
			lastDayOfYear.set(Calendar.YEAR, year);
			lastDayOfYear.set(Calendar.MONTH, 11);
			lastDayOfYear.set(Calendar.DAY_OF_MONTH, 31);
			
			List<Leaves> lstLeaves = leavesDao.getLeaves(employeeId, firstDayOfYear.getTime(), lastDayOfYear.getTime());
			if(lstLeaves!=null && lstLeaves.size() > 0) {
				Employee emp = lstLeaves.get(0).getEmployee();
				form.setEmployeeId(emp.getIdEmployee());
				form.setEmployeeName(emp.getFirstName() + " " + emp.getLastName());
				form.setLeavesAllocated(emp.getAllocatedLeaves());
				form.setCarriedForwardLeaves(emp.getCarriedForwardLeaves());
				
				Integer leavesAllocated = emp.getAllocatedLeaves();
				Integer leavesUsed = 0;
				Integer leavesRemaining = emp.getAllocatedLeaves();
				Integer sickLeavesUsed = 0;
				Integer unpaidLeavesUsed = 0;
				Integer leavesPendingApproval = 0;
				for(Leaves leave : lstLeaves) {
					/*
					 * Get Difference between the days
					 * */
					int days = (int) DateUtils.dateDiffExcludeWeekends(leave.getDtFrom(), leave.getDtTo());
					
					/*
					 * Vacation
					 * */
					if(leave.getLeaveType().getIdLeaveType().equals(Constants.LEAVE_TYPE_VACATION)) {
						if(leaveApproved(leave, emp.getApprovers().size())) {
							leavesUsed = leavesUsed + days;
							leavesRemaining = leavesRemaining - leavesUsed;
						} else {
							leavesPendingApproval = leavesPendingApproval + days;
						}
					}
					
					/*
					 * Sick
					 * */
					if(leave.getLeaveType().getIdLeaveType().equals(Constants.LEAVE_TYPE_SICK)) {
						if(leaveApproved(leave, emp.getApprovers().size())) {
							sickLeavesUsed = sickLeavesUsed + days;
						} else {
							leavesPendingApproval = leavesPendingApproval + days;
						}
					}
					
					/*
					 * Unpaid
					 * */
					if(leave.getLeaveType().getIdLeaveType().equals(Constants.LEAVE_TYPE_UNPAID)) {
						if(leaveApproved(leave, emp.getApprovers().size())) {
							unpaidLeavesUsed = unpaidLeavesUsed + days;
						} else {
							leavesPendingApproval = leavesPendingApproval + days;
						}
					}
				}
				
				form.setLeavesAllocated(leavesAllocated);
				form.setLeavesUsed(leavesUsed);
				form.setLeavesRemaining(leavesRemaining);
				form.setLeavesPendingApproval(leavesPendingApproval);
				form.setSickLeavesUsed(sickLeavesUsed);
				form.setUnpaidLeavesUsed(unpaidLeavesUsed);
			} else {
				//No Leaves found.
				Employee emp = loginDao.getEmployee(employeeId);
				form.setEmployeeId(emp.getIdEmployee());
				form.setEmployeeName(emp.getFirstName() + " " + emp.getLastName());
				form.setLeavesAllocated(emp.getAllocatedLeaves());
				form.setCarriedForwardLeaves(emp.getCarriedForwardLeaves());
				
				form.setLeavesAllocated(null);
				form.setLeavesUsed(null);
				form.setLeavesRemaining(null);
				form.setLeavesPendingApproval(null);
				form.setSickLeavesUsed(null);
				form.setUnpaidLeavesUsed(null);
			}
		} catch(Exception e) {
			logger.error("Exception in getLeavesForForm Service", e);
		}
		return form;
	}
	
	private boolean leaveApproved(Leaves leave, int numberOfApprovers) {
		/*
		 * Get Approvals for every leave applied. 
		 * If leave is approved by all approvers: leavesUsed = leavesUsed + days; && leavesRemaining = allocatedLeaves-leavesUsed;
		 * If leave not approved by even one approver - leavesPendingApproval + days;
		 * */
		boolean leaveApproved = false;
		Set<LeaveApprovals> leaveApprovals = leave.getLeaveApprovals();
		if(leaveApprovals!=null) {
			int approvals = 0;
			Iterator<LeaveApprovals> itr = leaveApprovals.iterator();
			while(itr.hasNext()) {
				LeaveApprovals approval = itr.next();
				if(!approval.getIsApproved()) {
					
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
		
		return leaveApproved;
	}

	@Override
	public void saveLeave(LeavesForm leavesForm) {
		try {
			Leaves leave = new Leaves();
			Employee emp = loginDao.getEmployee(leavesForm.getEmployeeId());
			leave.setEmployee(emp);
			leave.setDtFrom(dfStrToDb.parse(leavesForm.getDtFrom()));
			leave.setDtTo(dfStrToDb.parse(leavesForm.getDtTo()));
			leave.setDtAppliedOn(new Date());
			LeaveType leaveType = leavesDao.getLeaveType(leavesForm.getLeaveType());
			leave.setLeaveType(leaveType);
			leave.setLeaveDescription(leavesForm.getLeaveReason());
			
			Integer idLeave = leavesDao.saveLeave(leave);
			logger.info("Leave Saved - id: {}", idLeave);
			
			if(idLeave!=null) {
				//Send Email to the first Approver.
				Set<Approvers> approvers = emp.getApprovers();
				Iterator<Approvers> itr = approvers.iterator();
				Approvers firstApprover = itr.next();
				
				logger.info("First Approver for empId: {}, empName: {} is approverEmpId: {}, approverEmpName: {}", 
						new Object[] {emp.getIdEmployee(), emp.getFirstName(), 
								firstApprover.getApprover().getIdEmployee(), firstApprover.getApprover().getFirstName()});
				
				String emailTo = firstApprover.getApprover().getEmailId();
				String[] emailCc = {emp.getEmailId()};
				
				String subject = MessageFormat.format(emailSubject, emp.getFirstName());
				String body = MessageFormat.format(emailBody, firstApprover.getApprover().getFirstName(), 
						leave.getLeaveType().getLeaveType(), emp.getFirstName(), 
						leavesForm.getDtFrom(), leavesForm.getDtTo());
				emailService.sendEmail(emailTo, emailCc, body, subject);
			}
		} catch(Exception e) {
			logger.error("Exception in saveLeave Service",e);
		}
	}

	@Override
	public List<LeavesVo> getPendingLeaveApprovals(Integer approverId) {
		try {
			List<LeavesVo> lstLeavesVo = new ArrayList<LeavesVo>();
			List<Leaves> lstLeaves = leavesDao.getPendingLeaveApprovals(approverId);
			for(Leaves leave : lstLeaves) {
				/*
				 * Check in the approvals table. If this approver (with approverId) has approved the list or not.
				 * If he has approved the leave, do not add it to the List of LeavesVo otherwise add it. 
				 * */
				boolean isLeaveApprovedByThisApprover = false;
				Set<LeaveApprovals> approvals = leave.getLeaveApprovals();
				Iterator<LeaveApprovals> itr = approvals.iterator();
				while(itr.hasNext()) {
					LeaveApprovals leaveApproval = itr.next();
					if(leaveApproval.getApprover().getIdEmployee().equals(approverId)) {
						isLeaveApprovedByThisApprover = true;
					}
				}
				/*
				 * If leave is not approved by the approverId then add this leave to the list.
				 * */
				if(!isLeaveApprovedByThisApprover) {
					LeavesVo vo = new LeavesVo();
					vo.setEmployeeId(leave.getEmployee().getIdEmployee());
					vo.setEmployeeName(leave.getEmployee().getFirstName() + " " + leave.getEmployee().getLastName());
					vo.setIdLeave(leave.getIdLeaves());
					vo.setDtAppliedOn(dfStrToDb.format(leave.getDtAppliedOn()));
					vo.setDtFrom(dfStrToDb.format(leave.getDtFrom()));
					vo.setDtTo(dfStrToDb.format(leave.getDtTo()));
					vo.setLeaveType(leave.getLeaveType().getLeaveType());
					vo.setLeaveDescription(leave.getLeaveDescription());
					lstLeavesVo.add(vo);
				}
			}
			return lstLeavesVo;
		} catch(Exception e) {
			logger.error("Exception in service getPendingLeaveApprovals",e);
		}
		return null;
	}

	@Override
	public void approveLeave(Integer leaveId, Integer approverId, String notes) {
		try {
			logger.info("approveLeave - leaveId: {}, approverId: {}", leaveId, approverId);
			LeaveApprovals la = new LeaveApprovals();
			Employee approver = loginDao.getEmployee(approverId);
			Leaves leave = leavesDao.getLeaveById(leaveId);
			la.setApprover(approver);
			la.setDtUpdated(new Date());
			la.setIsApproved(true);
			la.setLeave(leave);
			la.setNotes(notes);
			leavesDao.saveOrUpdateLeaveApprovals(la);
			
			leave = leavesDao.getLeaveById(leaveId); //This is done again to load the list of leaveapprovals.
			/*
			 * Once approved. Check any more approvers left to approve this leave.
			 * */
			Set<Approvers> approvers = leave.getEmployee().getApprovers();
			Set<LeaveApprovals> approvals = leave.getLeaveApprovals();
			boolean mailSentToNextApprover = false;
			boolean isNextApprover = false;
			
			Iterator<Approvers> itrApprovers = approvers.iterator();
			while(itrApprovers.hasNext() && !mailSentToNextApprover) {
				Approvers appr = itrApprovers.next();
				Integer approverIdFromListOfApprovers = appr.getApprover().getIdEmployee();
				isNextApprover = true;
				/*
				 * Check in the set of approvals if the above appr.getApproverId has approved the leave. If approved loop next. 
				 * If not approved come out of loop and send email to that approver. 
				 * */
				Iterator<LeaveApprovals> itrLeaveApprovals = approvals.iterator();
				while(itrLeaveApprovals.hasNext() && !mailSentToNextApprover) {
					LeaveApprovals leaveApproval = itrLeaveApprovals.next();
					Integer approverIdInLeaveApproval = leaveApproval.getApprover().getIdEmployee();
					if(approverIdFromListOfApprovers.equals(approverIdInLeaveApproval)) {
						isNextApprover = false;
					}
				}
				
				if(isNextApprover) {
					String emailTo = appr.getApprover().getEmailId();
					String[] emailCc = {leave.getEmployee().getEmailId()};
					
					String subject = MessageFormat.format(emailSubject, leave.getEmployee().getFirstName());
					String body = MessageFormat.format(emailBody, appr.getApprover().getFirstName(), 
							leave.getLeaveType().getLeaveType(), leave.getEmployee().getFirstName(), 
							dfStrToDb.format(leave.getDtFrom()), dfStrToDb.format(leave.getDtTo()));
					emailService.sendEmail(emailTo, emailCc, body, subject);
					mailSentToNextApprover = true;
				}
			}
			
			logger.info("Leave id: {} Approved by: {} successfully and email sent to next approver.", leaveId, approverId);
		} catch(Exception e) {
			logger.error("Exception in service approveLeave",e);
		}
	}
	
	@Override
	public void rejectLeave(Integer leaveId, Integer approverId, String rejectionNotes) {
		try {
			logger.info("rejectLeave - leaveId: {}, approverId: {}", leaveId, approverId);
			LeaveApprovals la = new LeaveApprovals();
			Employee approver = loginDao.getEmployee(approverId);
			Leaves leave = leavesDao.getLeaveById(leaveId);
			la.setApprover(approver);
			la.setDtUpdated(new Date());
			la.setIsApproved(false);
			la.setLeave(leave);
			la.setNotes(rejectionNotes);
			leavesDao.saveOrUpdateLeaveApprovals(la);
			
			/*
			 * Send Rejection Email.
			 * */
			String emailTo = leave.getEmployee().getEmailId();
			String[] emailCc = {approver.getEmailId()};
			
			String subject = leaveRejectionEmailSubject;
			String body = MessageFormat.format(leaveRejectionEmailBody, leave.getEmployee().getFirstName(), 
					leave.getLeaveType().getLeaveType(), approver.getFirstName(), 
					dfStrToDb.format(leave.getDtFrom()), dfStrToDb.format(leave.getDtTo()), rejectionNotes);
			emailService.sendEmail(emailTo, emailCc, body, subject);
			
		} catch(Exception e) {
			logger.error("Exception in service approveLeave",e);
		}
	}

}
