package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class BusDailyRecordList {

    private String result;
    private int count;
    private ArrayList<BusDailyRecord> items;

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

    public ArrayList<BusDailyRecord> getItems() {
        return items;
    }

    public void setItems(ArrayList<BusDailyRecord> items) {
        this.items = items;
    }
}
