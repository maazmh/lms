package com.maaz.lms.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.maaz.lms.util.Constants;

public class LoginInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		HttpSession session = request.getSession();
		
		Integer empId = session.getAttribute(Constants.SESSION_STR_EMP_ID)!=null ? (Integer) session.getAttribute(Constants.SESSION_STR_EMP_ID) : null;
		logger.info("Employee Id: {}", empId);
		
		Integer companyId = session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID)!=null ? (Integer) session.getAttribute(Constants.SESSION_STR_COMP_ACT_ID) : null;
		logger.info("company Id: {}", companyId);
		
		if(empId == null || companyId == null) {
			response.sendRedirect("/lms/login");
		}
		
		return true;
	}
	@Override
	public void postHandle(	HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("---method executed---");
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		logger.info("---Request Completed---");
	}
}
