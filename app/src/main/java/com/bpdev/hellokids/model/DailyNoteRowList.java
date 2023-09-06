package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class DailyNoteRowList {
    private String result;

    private ArrayList<DailyNoteRow> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<DailyNoteRow> getItems() {
        return items;
    }

    public void setItems(ArrayList<DailyNoteRow> items) {
        this.items = items;
    }
}
