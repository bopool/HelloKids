package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class FoodMenuList {

    private String result;
    private int count;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<FoodMenuList> getItems() {
        return items;
    }

    public void setItems(ArrayList<FoodMenuList> items) {
        this.items = items;
    }

    private ArrayList<FoodMenuList> items;

}
