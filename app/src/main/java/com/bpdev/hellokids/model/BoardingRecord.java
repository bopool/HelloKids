package com.bpdev.hellokids.model;

public class BoardingRecord {
    String shuttleChidIn;
    String shuttleChildOut;

    public BoardingRecord(String shuttleChidIn, String shuttleChildOut) {
        this.shuttleChidIn = shuttleChidIn;
        this.shuttleChildOut = shuttleChildOut;
    }

    public String getShuttleChidIn() {
        return shuttleChidIn;
    }

    public void setShuttleChidIn(String shuttleChidIn) {
        this.shuttleChidIn = shuttleChidIn;
    }

    public String getShuttleChildOut() {
        return shuttleChildOut;
    }

    public void setShuttleChildOut(String shuttleChildOut) {
        this.shuttleChildOut = shuttleChildOut;
    }
}
