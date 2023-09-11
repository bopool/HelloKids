package com.bpdev.hellokids.model;

import java.io.Serializable;

public class FoodMenu implements Serializable {

    private int id;
    private String mealDate;
    private String mealPhotoUrl;
    private String mealContent;
    private String mealType;

    public FoodMenu(int id, String mealDate, String mealPhotoUrl, String mealContent, String mealType) {
        this.id = id;
        this.mealDate = mealDate;
        this.mealPhotoUrl = mealPhotoUrl;
        this.mealContent = mealContent;
        this.mealType = mealType;
    }

    public FoodMenu() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    public String getMealPhotoUrl() {
        return mealPhotoUrl;
    }

    public void setMealPhotoUrl(String mealPhotoUrl) {
        this.mealPhotoUrl = mealPhotoUrl;
    }

    public String getMealContent() {
        return mealContent;
    }

    public void setMealContent(String mealContent) {
        this.mealContent = mealContent;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
