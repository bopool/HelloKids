package com.bpdev.hellokids.model;

import java.io.Serializable;

public class AttendanceRes implements Serializable {

    private int id;
    private String childName;
    private String date;
    private String status;
    private String memo;

    public AttendanceRes(int id, String childName, String date, String status, String memo) {
        this.id = id;
        this.childName = childName;
        this.date = date;
        this.status = status;
        this.memo = memo;
    }

    public AttendanceRes(String status, String memo) {
        this.status = status;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
