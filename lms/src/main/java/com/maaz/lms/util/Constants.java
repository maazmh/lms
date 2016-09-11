package com.maaz.lms.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final Integer LEAVE_TYPE_VACATION = 1;
	public static final Integer LEAVE_TYPE_SICK = 2;
	public static final Integer LEAVE_TYPE_UNPAID = 3;
	
	public static final String SESSION_STR_EMP_ID = "employeeId";
	public static final String SESSION_STR_COMP_ACT_ID = "companyAccountId";
	
	private static Map<Integer, Integer> MAP_FISCAL_YEAR_ID=null;
	static {
		MAP_FISCAL_YEAR_ID = new HashMap<Integer, Integer>();
		MAP_FISCAL_YEAR_ID.put(2016,1);
		MAP_FISCAL_YEAR_ID.put(2017,2);
		MAP_FISCAL_YEAR_ID.put(2018,3);
	}
}
