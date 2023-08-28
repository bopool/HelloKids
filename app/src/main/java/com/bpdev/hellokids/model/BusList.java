package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class BusList {

    private String result;
    private int count;
    private ArrayList<Bus> items;

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

    public ArrayList<Bus> getItems() {
        return items;
    }

    public void setItems(ArrayList<Bus> items) {
        this.items = items;
    }
}
