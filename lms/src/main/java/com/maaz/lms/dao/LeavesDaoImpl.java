package com.maaz.lms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.maaz.lms.entity.Approvers;
import com.maaz.lms.entity.FiscalYear;
import com.maaz.lms.entity.LeaveApprovals;
import com.maaz.lms.entity.LeaveType;
import com.maaz.lms.entity.Leaves;
import com.maaz.lms.util.HibernateUtil;

@Service
public class LeavesDaoImpl implements LeavesDao {
	private static final Logger logger = LoggerFactory.getLogger(LeavesDaoImpl.class);

	@Override
	public List<Leaves> getLeaves(Integer employeeId) {
		try {
			logger.info("getLeaves - employeeId: {}", employeeId);
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Leaves where employee.idEmployee = :idEmployee");
			query.setParameter("idEmployee", employeeId);
			List<Leaves> list = query.list();
			logger.info("list size: {}", list!=null ? list.size() : null);
			return list;
		} catch(Exception e) {
			logger.error("Login Exception",e);
			return null;
		}
	}
	
	@Override
	public List<Leaves> getLeavesByDepartment(Integer companyAccountId, Integer departmentId) {
		try {
			logger.info("getLeavesByDepartment: companyAccountId: {}, departmentId: {}", companyAccountId, departmentId);
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Leaves as l where employee.department.idDepartment = :idDept");
			query.setParameter("idDept", departmentId);
			List<Leaves> list = query.list();
			logger.info("list size: {}", list!=null ? list.size() : null);
			return list;
		} catch(Exception e) {
			logger.error("Login Exception",e);
			return null;
		}
	}
	
	@Override
	public List<Leaves> getLeaves(Integer employeeId, Date dtFrom, Date dtTo) {
		try {
			logger.info("EmployeeId: {}, dtFrom, dtTo", new Object[] {employeeId, dtFrom, dtTo});
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Leaves where employee.idEmployee = :idEmployee and dtFrom between :dtFrom and :dtTo");
			query.setParameter("idEmployee", employeeId);
			query.setParameter("dtFrom", dtFrom);
			query.setParameter("dtTo", dtTo);
			List<Leaves> list = query.list();
			logger.info("list size: {}", list!=null ? list.size() : null);
			return list;
		} catch(Exception e) {
			logger.error("Login Exception",e);
			return null;
		}
	}

	@Override
	public List<LeaveType> getAllLeaveTypes() {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from LeaveType");
			List<LeaveType> list = query.list();
			logger.info("list size: {}", list!=null ? list.size() : null);
			return list;
		} catch(Exception e) {
			logger.error("Login Exception",e);
			return null;
		}
	}

	@Override
	public LeaveType getLeaveType(Integer idLeaveType) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			return (LeaveType) session.get(LeaveType.class, idLeaveType);
		} catch(Exception e) {
			logger.error("DAO Exception getLeaveType",e);
		}
		return null;
	}

	@Override
	public Integer saveLeave(Leaves leave) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Integer leaveId = (Integer) session.save(leave);
			return leaveId;
		} catch(Exception e) {
			logger.error("DAO Exception saveLeave",e);
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Leaves> getPendingLeaveApprovals(Integer approverId) {
		try {
			List<Leaves> leaves = new ArrayList<Leaves>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Approvers where idApprover = :approverId");
			query.setParameter("approverId", approverId);
			List<Approvers> list = query.list();
			for(Approvers approver : list) {
				leaves.addAll(approver.getEmployee().getLeaves());
			}
			logger.info("list size: {}", list!=null ? list.size() : null);
			return leaves;
		} catch(Exception e) {
			logger.error("getPendingLeaveApprovals Exception",e);
			return null;
		}
	}

	@Override
	public void saveOrUpdateLeaveApprovals(LeaveApprovals la) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.saveOrUpdate(la);
		} catch(Exception e) {
			logger.error("approveLeave Exception",e);
		} finally {
			session.close();
		}
		
	}

	@Override
	public Leaves getLeaveById(Integer leaveId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return (Leaves) session.get(Leaves.class, leaveId);
		} catch(Exception e) {
			logger.error("approveLeave Exception",e);
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<FiscalYear> getAllFiscalYears() {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from FiscalYear");
			List<FiscalYear> list = query.list();
			return list;
		} catch(Exception e) {
			logger.error("getAllFiscalYears Exception",e);
			return null;
		}
	}

	@Override
	public FiscalYear getFiscalYear(Integer fiscalYearId) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			return (FiscalYear) session.get(FiscalYear.class, fiscalYearId);
		} catch(Exception e) {
			logger.error("getFiscalYear Exception",e);
			return null;
		}
	}

	@Override
	public List<Leaves> searchLeaves(Integer companyAccountId, List<Integer> empIds, Integer dept, String dtFrom,
			String dtTo, Integer leaveType, Integer isApproved) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append("from Leaves l where l.employee.company.idCompanyAccount = " + companyAccountId);
			if(empIds!=null && empIds.size() > 0) {
				sbQuery.append(" and l.employee.idEmployee in (");
				for(Integer empId : empIds) {
					sbQuery.append(empId+",");
				}
				sbQuery = new StringBuilder(sbQuery.substring(0, sbQuery.length()-1));
			}
			sbQuery.append(")");
			if(dept!=null) {
				sbQuery.append(" and l.employee.department.idDepartment = " + dept);
			}
			if(dtFrom!=null && dtTo!=null) {
				sbQuery.append(" and dtFrom between " + dtFrom + " and " + dtTo);
			}
			if(leaveType!=null) {
				sbQuery.append(" and l.leaveType.idLeaveType = "+leaveType);
			}
			
			//@ TODO: isApproved is to be done. I commented this because i am thinking of changing the approval system.
//			if(isApproved!=null) {
//				sbQuery.append(" and l.isApproved = " + isApproved);
//			}
			
			logger.info("reports search query builder: {}", sbQuery.toString());
			
			Query query = session.createQuery(sbQuery.toString());
			List<Leaves> list = query.list();
			return list;
		} catch(Exception e) {
			logger.error("searchLeaves Exception",e);
			return null;
		}
	}
	
	
}
