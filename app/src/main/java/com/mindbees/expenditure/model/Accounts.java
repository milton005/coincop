package com.mindbees.expenditure.model;

import java.io.Serializable;

public class Accounts implements Serializable {

	String account_id;
	String user_id;
	String account_title;
	String added_date;
	String initial_amount;
	String final_balance;
	boolean selected;
	
	

	public Accounts() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public String getFinal_balance() {
		return final_balance;
	}



	public void setFinal_balance(String final_balance) {
		this.final_balance = final_balance;
	}



	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAccount_title() {
		return account_title;
	}

	public void setAccount_title(String account_title) {
		this.account_title = account_title;
	}

	public String getAdded_date() {
		return added_date;
	}

	public void setAdded_date(String added_date) {
		this.added_date = added_date;
	}

	public String getInitial_amount() {
		return initial_amount;
	}

	public void setInitial_amount(String initial_amount) {
		this.initial_amount = initial_amount;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	

}
