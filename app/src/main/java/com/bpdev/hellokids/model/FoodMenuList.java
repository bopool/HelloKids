package com.bpdev.hellokids.model;

import java.util.ArrayList;
public class FoodMenuList {

    private String result;
    private int count;
    private ArrayList<FoodMenu> items;

    public FoodMenuList() {
    }

    public FoodMenuList(String result, int count, ArrayList<FoodMenu> items) {
        this.result = result;
        this.count = count;
        this.items = items;
    }

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

    public ArrayList<FoodMenu> getItems() {
        return items;
    }

    public void setItems(ArrayList<FoodMenu> items) {
        this.items = items;
    }
}
