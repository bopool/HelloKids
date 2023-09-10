package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class NoticeRes {

    private String result;
    private int count;
    private ArrayList<Notice> items;

    public NoticeRes(String result, int count, ArrayList<Notice> items) {
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

    public ArrayList<Notice> getItems() {
        return items;
    }

    public void setItems(ArrayList<Notice> items) {
        this.items = items;
    }

    public NoticeRes() {
    }
}
