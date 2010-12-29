package org.blanco.lacuenta.db.entities;

import java.util.Calendar;

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
	
	
}
