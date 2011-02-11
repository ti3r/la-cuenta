package org.blanco.lacuenta.misc;

import java.util.Calendar;
/***
 * Static Class that will help to establish the low and high limits
 * of the passed load target (SplitsDataLoader.TODAY_LOAD, 
 * SplitsDataLoader.WEEK_LOAD or SplitsDataLoader.MONTH_LOAD). This calculate
 * will return the start and end of the current day, week or month 
 * in milliseconds format
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class CalendarUtilities {

	public static String[] getLoadLimits(String loadTarget){
		String[] limits = new String[]{"",""};
		Calendar cLow = Calendar.getInstance();
		Calendar cTop = Calendar.getInstance();
		
		cLow.setTimeInMillis(System.currentTimeMillis()); //set the calendar a this moment
		cTop.setTimeInMillis(System.currentTimeMillis());
		
		if (loadTarget.equals(SplitsDataLoader.MONTH_LOAD)){
			setStartOfMonthToCalendar(cLow);
			setEndOfMonthToCalendar(cTop);
		}
		if (loadTarget.equals(SplitsDataLoader.WEEK_LOAD)){
			setStartOfWeekToCalendar(cLow);
			setEndOfWeekToCalendar(cTop);
		}
		if (loadTarget.equals(SplitsDataLoader.TODAY_LOAD)){
			setStartOfDayToCalendar(cLow);
			setEndOfDayToCalendar(cTop);
		}
		limits[0] = String.valueOf(cLow.getTimeInMillis());
		limits[1] = String.valueOf(cTop.getTimeInMillis());
		return limits;
	}
	
	private static void setStartOfMonthToCalendar(Calendar c){
		c.set(Calendar.DAY_OF_MONTH,c.getMinimum(Calendar.DAY_OF_MONTH));
	}
	
	private static void setEndOfMonthToCalendar(Calendar c){
		c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH));
	}
	
	private static void setStartOfWeekToCalendar(Calendar c){
		c.set(Calendar.DAY_OF_WEEK, c.getMinimum(Calendar.DAY_OF_WEEK));
	}
	
	private static void setEndOfWeekToCalendar(Calendar c){
		c.set(Calendar.DAY_OF_WEEK, c.getMaximum(Calendar.DAY_OF_WEEK));
	}
	
	private static void setStartOfDayToCalendar(Calendar c){
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
	}
	
	private static void setEndOfDayToCalendar(Calendar c){
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
	}
	
}
