package com.bpdev.hellokids.model;


import java.io.Serializable;
import java.sql.Date;

public class Schedule implements Serializable {

    private int classId;
    private String title;
    private String contents;
    private String date;

    public Schedule(int classId, String title, String contents, String date) {
        this.classId = classId;
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
