package com.google.gwt.sample.healthyeatingapp.client;

import java.io.Serializable;


public class FoodLogItems implements Serializable {
	@SuppressWarnings("unused")
	private String foodName;
	private int calories;
	private String foodGroup;

	
	
	 public FoodLogItems(String foodName, int calories, String foodGroup) {
		  this.foodName = foodName; 
		  this.calories = calories;
		  this.foodGroup = foodGroup;

	 }
	 
	 
	 public String FoodName() {
		    return foodName;
		  }
	 
	 public int Calories() {
		    return calories;
		  }
	 
	 public String FoodGroup() {
		    return foodGroup;
		  }	 
	 	 
	
	 
	 public void setFoodName(String fn){
			this.foodName = fn;
		}
		
		public void setCalories(int cal){
			this.calories = cal;
		}
		
		public void setFoodGroup(String fg){
			this.foodGroup = fg;
		}
}