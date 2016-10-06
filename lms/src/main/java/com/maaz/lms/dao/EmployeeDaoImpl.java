package com.maaz.lms.dao;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.maaz.lms.controller.LoginController;
import com.maaz.lms.entity.Approvers;
import com.maaz.lms.entity.CompanyAccount;
import com.maaz.lms.entity.Department;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.entity.EmployeeFiscalYearLeaves;
import com.maaz.lms.entity.FiscalYear;
import com.maaz.lms.form.AdminForm;
import com.maaz.lms.util.HibernateUtil;

@Service
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LeavesDao leavesDao;

	@Override
	public Employee getEmployee(Integer employeeId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return (Employee) session.get(Employee.class, employeeId);
		} catch(Exception e) {
			logger.error("DAO Exception getLeaveType",e);
		} finally {
			session.close();
		}
		return null;
	}
	
	@Override
	public Employee getEmployee(Integer companyId, Integer employeeId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Employee where company.idCompanyAccount = :idCompanyAccount and idEmployee = :idEmployee");
			query.setParameter("idCompanyAccount", companyId);
			query.setParameter("idEmployee", employeeId);
			List<Employee> lstEmp = query.list();
			if(lstEmp!=null && lstEmp.size()>0) {
				return lstEmp.get(0);
			}
		} catch(Exception e) {
			logger.error("DAO Exception getEmployee",e);
		} finally {
			session.close();
		}
		return null;
	}


	@Override
	public List<Employee> getAllEmployees(Integer companyAccountId) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Employee where company.idCompanyAccount = :idCompanyAccount");
			query.setParameter("idCompanyAccount", companyAccountId);
			return query.list();
		} catch(Exception e) {
			logger.error("DAO Exception getAllEmployees",e);
		}
		return null;
	}


	@Override
	public List<Department> getAllDepartments() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Department");
			return query.list();
		} catch(Exception e) {
			logger.error("DAO Exception getAllDepartments",e);
		} finally {
			session.close();
		}
		return null;
	}


	@Override
	public void saveEmployee(AdminForm form, Employee emp) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			
			emp = new Employee();
			CompanyAccount company = (CompanyAccount) session.get(CompanyAccount.class, form.getCompanyAccountId());
			emp.setCompany(company);
			emp.setIdEmployee(form.getEmployeeId());
			emp.setFirstName(form.getFirstName());
			emp.setLastName(form.getLastName());
			emp.setEmailId(form.getEmailId());
			emp.setReportsTo((Employee) session.get(Employee.class, form.getReportsTo()));
			Department dept = (Department) session.get(Department.class, form.getDepartment());
			emp.setDepartment(dept);
			emp.setAdmin(form.getAdmin().equals(0) ? false : true);
			emp.setDeleted(form.getDeleted().equals(0) ? false : true);
			
			Set<Approvers> setApprovers = null;
			if(form.getApprovers()!=null && form.getApprovers().size()>0) {
				setApprovers = new HashSet<Approvers>();
				for(Integer approverId : form.getApprovers()) {
					Approvers approvers = new Approvers();
					Employee approver = (Employee) session.get(Employee.class, approverId);
					approvers.setEmployee(emp);
					approvers.setApprover(approver);
					setApprovers.add(approvers);
				}
			}
			emp.setApprovers(setApprovers);
			
			Calendar now = Calendar.getInstance();
			EmployeeFiscalYearLeaves empFisc = new EmployeeFiscalYearLeaves();
			empFisc.setEmployee(emp);
			empFisc.setLeavesAllocated(form.getLeavesAllocated());
			empFisc.setLeavesCarriedForward(form.getLeavesCarriedForward());
			int yearNow = now.get(Calendar.YEAR);
			List<FiscalYear> lstFiscalYrs = leavesDao.getAllFiscalYears();
			for(FiscalYear fy : lstFiscalYrs) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(fy.getDtTo());
				if(yearNow==cal.get(Calendar.YEAR)) {
					empFisc.setFiscalYear(fy);
				}
			}
			Set<EmployeeFiscalYearLeaves> setEmpFisc = new HashSet<EmployeeFiscalYearLeaves>();
			setEmpFisc.add(empFisc);
			emp.setEmpFiscalYrLeaves(setEmpFisc);
			
			
			session.save(emp);
		} catch(Exception e) {
			logger.error("DAO Exception saveEmployee",e);
		} finally {
			session.flush();
			session.close();
		}
		
	}
	
	@Override
	public void updateEmployee(AdminForm form, Employee emp) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			emp = (Employee) session.get(Employee.class, form.getEmployeeId());
			emp.setIdEmployee(form.getEmployeeId());
			emp.setFirstName(form.getFirstName());
			emp.setLastName(form.getLastName());
			emp.setEmailId(form.getEmailId());
			Department dept = (Department) session.get(Department.class, form.getDepartment());
			emp.setDepartment(dept);
			emp.setAdmin(form.getAdmin().equals(0) ? false : true);
			emp.setDeleted(form.getDeleted().equals(0) ? false : true);
			emp.setReportsTo((Employee) session.get(Employee.class, form.getReportsTo()));
			emp.setCompany((CompanyAccount) session.get(CompanyAccount.class, form.getCompanyAccountId()));
			
			emp.getApprovers().clear();
			session.flush();
			if(form.getApprovers()!=null && form.getApprovers().size()>0) {
				for(Integer approverId : form.getApprovers()) {
					Approvers approvers = new Approvers();
					Employee approver = (Employee) session.get(Employee.class, approverId);
					approvers.setEmployee(emp);
					approvers.setApprover(approver);
					emp.getApprovers().add(approvers);
				}
			}
			
			Calendar now = Calendar.getInstance();
			EmployeeFiscalYearLeaves empFisc = new EmployeeFiscalYearLeaves();
			empFisc.setEmployee(emp);
			empFisc.setLeavesAllocated(form.getLeavesAllocated());
			empFisc.setLeavesCarriedForward(form.getLeavesCarriedForward());
			int yearNow = now.get(Calendar.YEAR);
			List<FiscalYear> lstFiscalYrs = leavesDao.getAllFiscalYears();
			for(FiscalYear fy : lstFiscalYrs) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(fy.getDtTo());
				if(yearNow==cal.get(Calendar.YEAR)) {
					empFisc.setFiscalYear(fy);
				}
			}
			emp.getEmpFiscalYrLeaves().clear();
			session.flush();
			emp.getEmpFiscalYrLeaves().add(empFisc);
			
			session.merge(emp);
		} catch(Exception e) {
			logger.error("DAO Exception UpdateEmployee",e);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	
	@Override
	public void deleteApprovers(Approvers approver) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.delete(approver);
		} catch(Exception e) {
			logger.error("DAO Exception saveOrUpdateEmployee",e);
		} finally {
			session.close();
		}
		
	}
	


	@Override
	public Department getDepartment(Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return (Department) session.get(Department.class, departmentId);
		} catch(Exception e) {
			logger.error("DAO Exception getDepartment",e);
		} finally {
			session.close();
		}
		return null;
	}


	@Override
	public CompanyAccount getCompany(Integer companyAccountId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return (CompanyAccount) session.get(CompanyAccount.class, companyAccountId);
		} catch(Exception e) {
			logger.error("DAO Exception getLeaveType",e);
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Employee> getAllEmployeesByDepartment(Integer companyAccountId, Integer dept) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Employee where company.idCompanyAccount = :idCompanyAccount and department.idDepartment = :idDepartment");
			query.setParameter("idCompanyAccount", companyAccountId);
			query.setParameter("idDepartment", dept);
			return query.list();
		} catch(Exception e) {
			logger.error("DAO Exception getAllEmployeesByDepartment",e);
		}
		return null;
	}

}
