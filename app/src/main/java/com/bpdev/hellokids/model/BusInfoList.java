package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class BusInfoList {

    private String result;
    private int count;
    private ArrayList<BusInfo> items;

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

    public ArrayList<BusInfo> getItems() {
        return items;
    }

    public void setItems(ArrayList<BusInfo> items) {
        this.items = items;
    }
}
