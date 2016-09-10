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
	public void saveEmployee(Employee emp) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.save(emp);
		} catch(Exception e) {
			logger.error("DAO Exception saveEmployee",e);
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
	
	@Override
	public void updateEmployee(Employee emp) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			session.update(emp);
		} catch(Exception e) {
			logger.error("DAO Exception UpdateEmployee",e);
			e.printStackTrace();
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
			e.printStackTrace();
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

}
