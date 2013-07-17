package com.google.gwt.sample.healthyeatingapp.client;

import java.io.Serializable;

public class FoodLogData implements Serializable {
	private String userName;
	private String foodName;
	private int calories;
	private String foodGroup;
	private String Date;

	public FoodLogData(String userName, String foodName, int calories,
			String foodGroup, String Date) {

		this.userName = userName;
		this.foodName = foodName;
		this.calories = calories;
		this.foodGroup = foodGroup;
		this.Date = Date;
	}

	public String getUserName() {
		return userName;
	}

	public String getFoodName() {
		return foodName;
	}

	public int getCalories() {
		return calories;
	}

	public String getFoodGroup() {
		return foodGroup;
	}

	public String getDate() {
		return Date;
	}

	public void setUserID(String un) {
		this.userName = un;
	}

	public void setFoodName(String fn) {
		this.foodName = fn;
	}

	public void setCalories(int cal) {
		this.calories = cal;
	}

	public void setFoodGroup(String fg) {
		this.foodGroup = fg;
	}

	public void setDate(String date) {
		this.Date = date;
	}

}
