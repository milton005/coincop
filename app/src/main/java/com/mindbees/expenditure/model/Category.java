package com.mindbees.expenditure.model;

import java.io.Serializable;

public class Category implements Serializable{
	private String catId;
	private String catTitlle;
	private String type_id;
	private String user_id;
	private String added_date;
	private String cat_image;
	private String cat_color;
	private boolean selected;
	
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getCat_color() {
		return cat_color;
	}



	public void setCat_color(String cat_color) {
		this.cat_color = cat_color;
	}



	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getCatTitlle() {
		return catTitlle;
	}
	public void setCatTitlle(String catTitlle) {
		this.catTitlle = catTitlle;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
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
	public String getCat_image() {
		return cat_image;
	}
	public void setCat_image(String cat_image) {
		this.cat_image = cat_image;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	

}
