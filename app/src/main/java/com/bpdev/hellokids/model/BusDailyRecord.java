package com.bpdev.hellokids.model;

import java.io.Serializable;

public class BusDailyRecord implements Serializable {

    private int id;
    private int shuttleTeacherId;
    private int schoolbusId;
    private String shuttleName;
    private String shuttleStart;
    private String shuttleStop;

    public BusDailyRecord(int id, String shuttleName, String shuttleStart, String shuttleStop) {
        this.id = id;
        this.shuttleName = shuttleName;
        this.shuttleStart = shuttleStart;
        this.shuttleStop = shuttleStop;
    }

    public BusDailyRecord(int shuttleTeacherId, int schoolbusId) {
        this.shuttleTeacherId = shuttleTeacherId;
        this.schoolbusId = schoolbusId;
    }

    public BusDailyRecord(String shuttleStart, String shuttleStop) {
        this.shuttleStart = shuttleStart;
        this.shuttleStop = shuttleStop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShuttleName() {
        return shuttleName;
    }

    public void setShuttleName(String shuttleName) {
        this.shuttleName = shuttleName;
    }

    public String getShuttleStart() {
        return shuttleStart;
    }

    public void setShuttleStart(String shuttleStart) {
        this.shuttleStart = shuttleStart;
    }

    public String getShuttleStop() {
        return shuttleStop;
    }

    public void setShuttleStop(String shuttleStop) {
        this.shuttleStop = shuttleStop;
    }

    public int getShuttleTeacherId() {
        return shuttleTeacherId;
    }

    public void setShuttleTeacherId(int shuttleTeacherId) {
        this.shuttleTeacherId = shuttleTeacherId;
    }

    public int getSchoolbusId() {
        return schoolbusId;
    }

    public void setSchoolbusId(int schoolbusId) {
        this.schoolbusId = schoolbusId;
    }
}
