/***
 *  La-Cuenta for Android, a Small application that allows users to split
 *  the restaurant check between the people that assists.
 *  Copyright (C) 2011  Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blanco.lacuenta.misc;

import java.text.DateFormat;
import java.util.Calendar;

import org.blanco.lacuenta.SplitsActivity;

import android.util.Log;
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

	/***
	 * Inner Class that will carry the low and top limits of the calendar
	 * calculations. It Contains a toStringArray method in order to be
	 * integrated easily with cursosrs.
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	public static class CalendarLimits{
		public CalendarLimits(long low, long top){
			this.lowLimit=low; this.topLimit = top;
		}
		public long lowLimit = 0;
		public long topLimit = 0;
		public String[] toStringArray(){
			return new String[]{String.valueOf(lowLimit), String.valueOf(topLimit)};
		}
	}
	
	public static CalendarLimits getLoadLimits(String loadTarget){
		Calendar cLow = Calendar.getInstance(); //Default Instances both for today
		Calendar cTop = Calendar.getInstance();
		
		cLow.setTimeInMillis(System.currentTimeMillis()); //set the calendar a this moment
		cTop.setTimeInMillis(System.currentTimeMillis());
		
		if (loadTarget.equals(SplitsActivity.MONTH_LOAD)){
			setStartOfMonthToCalendar(cLow);
			setEndOfMonthToCalendar(cTop);
		}
		if (loadTarget.equals(SplitsActivity.WEEK_LOAD)){
			setStartOfWeekToCalendar(cLow);
			setEndOfWeekToCalendar(cTop);
		}
		if (loadTarget.equals(SplitsActivity.TODAY_LOAD)){
			setStartOfDayToCalendar(cLow);
			setEndOfDayToCalendar(cTop);
		}
		return new CalendarLimits(cLow.getTimeInMillis(), cTop.getTimeInMillis());
	}
	
	private static void setStartOfMonthToCalendar(Calendar c){
		c.set(Calendar.DAY_OF_MONTH,c.getMinimum(Calendar.DAY_OF_MONTH));
		setStartOfDayToCalendar(c);
		Log.d("La Cuenta: ", "Min of Current Month: "+DateFormat.getInstance().format(c.getTime()));
	}
	
	private static void setEndOfMonthToCalendar(Calendar c){
		//Get the First Day of next month so it will e considered until 0 hours of next month
		int max= c.getMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, max);
		setEndOfDayToCalendar(c);		
		Log.d("La Cuenta: ", "Max of Current Month: "+DateFormat.getInstance().format(c.getTime()));
	}
	
	private static void setStartOfWeekToCalendar(Calendar c){
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		setStartOfDayToCalendar(c);
		Log.d("La Cuenta: ", "Min of Current Week: "+DateFormat.getInstance().format(c.getTime()));
	}
	
	private static void setEndOfWeekToCalendar(Calendar c){
		//Get First Day of next week so it will be considered until 0 hrs of next week
		c.set(Calendar.DAY_OF_WEEK, c.getMaximum(Calendar.DAY_OF_WEEK));
		setEndOfDayToCalendar(c);
		Log.d("La Cuenta: ", "Max of Current Week: "+DateFormat.getInstance().format(c.getTime()));
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
