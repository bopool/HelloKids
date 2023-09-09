package com.bpdev.hellokids.model;

public class Attendance {

    private int childId;
    private int classId;
    private String date;
    private String status;
    private String memo;

    public Attendance(int childId, int classId, String date, String status, String memo) {
        this.childId = childId;
        this.classId = classId;
        this.date = date;
        this.status = status;
        this.memo = memo;
    }

    public Attendance(int classId, String date, String status, String memo) {
        this.classId = classId;
        this.date = date;
        this.status = status;
        this.memo = memo;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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
