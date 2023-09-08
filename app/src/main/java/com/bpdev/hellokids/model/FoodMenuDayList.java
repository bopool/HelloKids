package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class FoodMenuDayList {

    public String TextDate;
    public ArrayList<FoodMenu> foodMenuArrayList;

    public FoodMenuDayList(String textDate, ArrayList<FoodMenu> foodMenuArrayList) {
        TextDate = textDate;
        this.foodMenuArrayList = foodMenuArrayList;
    }

    public FoodMenuDayList() {
    }

    public String getTextDate() {
        return TextDate;
    }

    public void setTextDate(String textDate) {
        TextDate = textDate;
    }

    public ArrayList<FoodMenu> getFoodMenuArrayList() {
        return foodMenuArrayList;
    }

    public void setFoodMenuArrayList(ArrayList<FoodMenu> foodMenuArrayList) {
        this.foodMenuArrayList = foodMenuArrayList;
    }
}
