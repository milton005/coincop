package com.mindbees.expenditure.model;

import java.io.Serializable;

public class ReminderSet implements Serializable{
	
	private String desc;
	private String date;
	private String time;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
