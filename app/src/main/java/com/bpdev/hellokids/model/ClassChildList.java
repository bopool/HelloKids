package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class ClassChildList {

    private String result;
    private int count;
    private ArrayList<ClassChild> items;

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

    public ArrayList<ClassChild> getItems() {
        return items;
    }

    public void setItems(ArrayList<ClassChild> items) {
        this.items = items;
    }
}
