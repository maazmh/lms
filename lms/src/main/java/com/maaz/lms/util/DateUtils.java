package com.maaz.lms.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static long dateDiffExcludeWeekends(Date from, Date to) {
		Calendar startCal = Calendar.getInstance();
	    startCal.setTime(from);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(to);
		
	    int workDays = 0;
	    while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
	    	if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
	            ++workDays;
	        }
	    	startCal.add(Calendar.DAY_OF_MONTH, 1);
	    }

	    return workDays;
	}
}
