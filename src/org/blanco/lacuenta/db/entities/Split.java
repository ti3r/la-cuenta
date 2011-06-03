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
package org.blanco.lacuenta.db.entities;

import java.util.Calendar;

import org.blanco.lacuenta.db.SPLITSContentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/***
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * 
 * Class that will represent one record on the database.
 * This class will improve the communication between the 
 * application and the Content Provider
 */
public class Split {

	// Content values keys (using column names)
	public static final String _ID = "_ID";
	public static final String TOTAL = "TOTAL";
	public static final String TIP = "TIP";
	public static final String PEOPLE = "PEOPLE";
	public static final String RESULT = "RESULT";
	public static final String DATE = "DATE";

	private long id;
	private double total;
	private int tip;
	private int people;
	private double result;
	private long date;
	
	
	
	public Split(double total, int tip, int people, double result) {
		this(-1,total,tip,people , result, Calendar.getInstance().getTimeInMillis());
	}

	public Split(double total, int tip, int people, double result, long date) {
		this(-1,total,tip,people,result,date);
	}
	
	public Split(long id, double total, int tip, int people, double result,	long date) {
		super();
		this.id = id;
		this.total = total;
		this.tip = tip;
		this.people = people;
		this.result = result;
		this.date = date;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getTip() {
		return tip;
	}
	public void setTip(int tip) {
		this.tip = tip;
	}
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
	/***
	 * This method is intended to create a new Split object based in the current position
	 * of the passed Cursor. If one of the fields of the object is not present in the
	 * cursor the corresponding property would be given a default value.
	 * @param c
	 */
	public static Split fromCurrentCursorPosition(Cursor c){
		long id = -1, millis = 0; double total = 0, result = 0; int tip=0, people = 0;
		
		if (c.getColumnIndex(Split._ID) >= 0)
			id = c.getLong(c.getColumnIndex(Split._ID));
		if (c.getColumnIndex(Split.DATE) >= 0)
			millis = c.getLong(c.getColumnIndex(Split.DATE));
		if (c.getColumnIndex(Split.PEOPLE) >= 0)
			people = c.getInt(c.getColumnIndex(Split.PEOPLE));
		if (c.getColumnIndex(Split.RESULT) >= 0)
			result = c.getDouble(c.getColumnIndex(Split.RESULT));
		if (c.getColumnIndex(Split.TIP) >= 0)
			tip = c.getInt(c.getColumnIndex(Split.TIP));
		if (c.getColumnIndex(Split.PEOPLE) >= 0)
			people = c.getInt(c.getColumnIndex(Split.PEOPLE));
		if (c.getColumnIndex(Split.TOTAL) >= 0)
			total = c.getDouble(c.getColumnIndex(Split.TOTAL));
		return new Split(id, total, tip, people, result, millis);
	}	

	/***
	 * This method will insert a new split into the splits database, 
	 * it will retrieve a content resolver from passed context and
	 * insert the split values
	 * @param split the Split object to insert
	 * @param ctx the Context object where to retrieve the content resolver from
	 * @return the created URI of the new object
	 */
	public static Uri insert(Split split, Context ctx){
		ContentResolver cr = ctx.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Split.DATE, split.getDate());
		values.put(Split.PEOPLE, split.getPeople());
		values.put(Split.RESULT, split.getResult());
		values.put(Split.TIP, split.getTip());
		values.put(Split.TOTAL, split.getTotal());
		Uri uri = cr.insert(SPLITSContentProvider.CONTENT_URI, values);
		
		return uri;
	}
}
