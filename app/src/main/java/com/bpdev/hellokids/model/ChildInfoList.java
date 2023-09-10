package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class ChildInfoList {
    private String result;
    private ArrayList<ChildInfo> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<ChildInfo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ChildInfo> items) {
        this.items = items;
    }
}
