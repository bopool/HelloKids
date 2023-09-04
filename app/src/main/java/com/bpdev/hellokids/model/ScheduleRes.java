package com.bpdev.hellokids.model;

import java.io.Serializable;

public class ScheduleRes implements Serializable {

    private int id;
    private int classId;
    private String title;
    private String contents;
    private String date;
    private int selectIcon;

    public ScheduleRes(int id, int classId, String title, String contents, String date, int selectIcon) {
        this.id = id;
        this.classId = classId;
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.selectIcon = selectIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSelectIcon() {
        return selectIcon;
    }

    public void setSelectIcon(int selectIcon) {
        this.selectIcon = selectIcon;
    }
}
