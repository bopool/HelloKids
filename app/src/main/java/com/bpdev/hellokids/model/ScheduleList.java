package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class ScheduleList {

    private String result;
    private int count;
    private ArrayList<ScheduleRes> items;

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

    public ArrayList<ScheduleRes> getItems() {
        return items;
    }

    public void setItems(ArrayList<ScheduleRes> items) {
        this.items = items;
    }
}
