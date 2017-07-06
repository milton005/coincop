package com.mindbees.expenditure.model;

import java.io.Serializable;

public class CalendarE implements Serializable {

	private int day;
	private int whichDay; // 0-grey-trailing day-leading day, 1-red circle with white- curnt day , 2-white-other days
	private int month;
	private int prevYear;
	private CalendarE data;
	private boolean selected;
	
	
	
	public CalendarE() {
	}
	public CalendarE(int day, int whichDay, int month, int prevYear, boolean selected) {
		super();
		this.day = day;
		this.whichDay = whichDay;
		this.month = month;
		this.prevYear = prevYear;
		this.selected = selected;
	}
	public int getDay() {
		return day;
	}
	public int setDay(int day) {
		this.day = day;
		return day;
	}
	public int getWhichDay() {
		return whichDay;
	}
	public void setWhichDay(int whichDay) {
		this.whichDay = whichDay;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getPrevYear() {
		return prevYear;
	}
	public void setPrevYear(int prevYear) {
		this.prevYear = prevYear;
	}
	public Object getData() {
		return data;
	}
	public CalendarE setData(CalendarE data) {
		this.data = data;
		return data;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	


}
