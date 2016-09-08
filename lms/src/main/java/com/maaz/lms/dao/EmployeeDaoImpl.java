package com.maaz.lms.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.maaz.lms.controller.LoginController;
import com.maaz.lms.entity.Department;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.util.HibernateUtil;

@Service
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Override
	public Employee getEmployee(Integer employeeId) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			return (Employee) session.get(Employee.class, employeeId);
		} catch(Exception e) {
			logger.error("DAO Exception getLeaveType",e);
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
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Department");
			return query.list();
		} catch(Exception e) {
			logger.error("DAO Exception getAllDepartments",e);
		}
		return null;
	}


	@Override
	public void saveOrUpdateEmployee(Employee emp) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.saveOrUpdate(emp);
		} catch(Exception e) {
			logger.error("DAO Exception saveOrUpdateEmployee",e);
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}


	@Override
	public Department getDepartment(Integer departmentId) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			return (Department) session.get(Department.class, departmentId);
		} catch(Exception e) {
			logger.error("DAO Exception getDepartment",e);
		}
		return null;
	}

}
