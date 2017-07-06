package com.mindbees.expenditure.model;

public class AllReminders {

	private String reminder_id;
	private String reminder_text;
	private String reminder_date;
	private String user_id;
	private String added_date;

	public String getReminder_id() {
		return reminder_id;
	}

	public void setReminder_id(String reminder_id) {
		this.reminder_id = reminder_id;
	}

	public String getReminder_text() {
		return reminder_text;
	}

	public void setReminder_text(String reminder_text) {
		this.reminder_text = reminder_text;
	}

	public String getReminder_date() {
		return reminder_date;
	}

	public void setReminder_date(String reminder_date) {
		this.reminder_date = reminder_date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAdded_date() {
		return added_date;
	}

	public void setAdded_date(String added_date) {
		this.added_date = added_date;
	}

}
