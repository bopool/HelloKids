package com.bpdev.hellokids.model;

import java.io.Serializable;

public class Bus implements Serializable {

    private int id; // 버스 운행기록 id
    private String shuttleName;
    private String shuttleNum;
    private String shuttleTime;
    private String shuttleDriver;
    private String shuttleDriverNum;

    public Bus(String shuttleName, String shuttleNum, String shuttleTime, String shuttleDriverName, String shuttleDriverNum) {
        this.shuttleName = shuttleName;
        this.shuttleNum = shuttleNum;
        this.shuttleTime = shuttleTime;
        this.shuttleDriver = shuttleDriverName;
        this.shuttleDriverNum = shuttleDriverNum;
    }

    public String getShuttleName() {
        return shuttleName;
    }

    public void setShuttleName(String shuttleName) {
        this.shuttleName = shuttleName;
    }

    public String getShuttleNum() {
        return shuttleNum;
    }

    public void setShuttleNum(String shuttleNum) {
        this.shuttleNum = shuttleNum;
    }

    public String getShuttleTime() {
        return shuttleTime;
    }

    public void setShuttleTime(String shuttleTime) {
        this.shuttleTime = shuttleTime;
    }

    public String getShuttleDriver() {
        return shuttleDriver;
    }

    public void setShuttleDriver(String shuttleDriverName) {
        this.shuttleDriver = shuttleDriverName;
    }

    public String getShuttleDriverNum() {
        return shuttleDriverNum;
    }

    public void setShuttleDriverNum(String shuttleDriverNum) {
        this.shuttleDriverNum = shuttleDriverNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
