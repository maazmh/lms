package com.maaz.lms.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.maaz.lms.controller.LoginController;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.util.HibernateUtil;

@Service
@Repository
public class LoginDaoImpl implements LoginDao {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	
	@Override
	public Integer login(String username, String password) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Employee where emailId = :username  and password = :password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			List<Employee> list = query.list();
			logger.info("list size: {}", list!=null ? list.size() : null);
			return list!=null ? list.get(0).getIdEmployee() : null;
		} catch(Exception e) {
			logger.error("Login Exception",e);
			return null;
		}
	}


	@Override
	public void changePassword(String username, String password) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("update Employee set password = :password" +
    				" where emailId = :emailId");
			query.setParameter("password", password);
			query.setParameter("emailId", username);
			int result = query.executeUpdate();
		} catch(Exception e) {
			logger.error("DAO Exception changePassword",e);
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
}
