package com.maaz.lms.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.maaz.lms.entity.LeaveType;
import com.maaz.lms.entity.Leaves;
import com.maaz.lms.util.HibernateUtil;

@Service
public class LeavesDaoImpl implements LeavesDao {
	private static final Logger logger = LoggerFactory.getLogger(LeavesDaoImpl.class);

	@Override
	public List<Leaves> getLeaves(Integer employeeId) {
		try {
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
	
	
}
