package com.mindbees.expenditure.database;

public class CurrencySymbol {

	private String crncyName;
	private String crncyCountry;
	private String crncySymbol;
	private boolean selected;

	public CurrencySymbol() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CurrencySymbol(String crncyName, String crncyCountry,
			String crncySymbol) {
		super();
		this.crncyName = crncyName;
		this.crncyCountry = crncyCountry;
		this.crncySymbol = crncySymbol;
	}

	public String getCrncyCountry() {
		return crncyCountry;
	}

	public void setCrncyCountry(String crncyCountry) {
		this.crncyCountry = crncyCountry;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getCrncyName() {
		return crncyName;
	}

	public void setCrncyName(String crncyName) {
		this.crncyName = crncyName;
	}

	public String getCrncySymbol() {
		return crncySymbol;
	}

	public void setCrncySymbol(String crncySymbol) {
		this.crncySymbol = crncySymbol;
	}

}
