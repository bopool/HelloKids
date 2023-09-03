package com.bpdev.hellokids.model;

public class FoodMenu {
    private String mealPhotoUrl;
    private String mealContent;
    private String mealType;
    private int nurseryId;

    public FoodMenu() {
    }


    public int getNurseryId() {
        return nurseryId;
    }

    public void setNurseryId(int nurseryId) {
        this.nurseryId = nurseryId;
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

    public FoodMenu(int nurseryId, String mealPhotoUrl, String mealContent, String mealType) {
        this.nurseryId = nurseryId;
        this.mealPhotoUrl = mealPhotoUrl;
        this.mealContent = mealContent;
        this.mealType = mealType;
    }


}
