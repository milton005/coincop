package com.mindbees.expenditure.model;

public class StatusAccount {

	private String account_id;
	private String userid;
	private String account_tittle;
	private String addedDate;
	private String initial_amount;
	private String final_balance;
	private int number;
	

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccount_tittle() {
		return account_tittle;
	}

	public void setAccount_tittle(String account_tittle) {
		this.account_tittle = account_tittle;
	}

	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public String getInitial_amount() {
		return initial_amount;
	}

	public void setInitial_amount(String initial_amount) {
		this.initial_amount = initial_amount;
	}

	public String getFinal_balance() {
		return final_balance;
	}

	public void setFinal_balance(String final_balance) {
		this.final_balance = final_balance;
	}

}
