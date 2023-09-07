package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class TeacherList {

    private String result;
    private int count;
    private ArrayList<Teacher> items;

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

    public ArrayList<Teacher> getItems() {
        return items;
    }

    public void setItems(ArrayList<Teacher> items) {
        this.items = items;
    }
}
