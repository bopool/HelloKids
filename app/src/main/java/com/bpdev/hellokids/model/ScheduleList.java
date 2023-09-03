package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class ScheduleList {

    private String result;
    private int count;
    private ArrayList<Schedule> items;

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

    public ArrayList<Schedule> getItems() {
        return items;
    }

    public void setItems(ArrayList<Schedule> items) {
        this.items = items;
    }
}
