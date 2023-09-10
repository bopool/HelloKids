package com.bpdev.hellokids.model;

import java.io.Serializable;

public class NurseryRes implements Serializable {
    private int id;
    private String nurseryName;
    private String nurseryAddress;

    public NurseryRes(int id, String nurseryName, String nurseryAddress) {
        this.id = id;
        this.nurseryName = nurseryName;
        this.nurseryAddress = nurseryAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public String getNurseryAddress() {
        return nurseryAddress;
    }

    public void setNurseryAddress(String nurseryAddress) {
        this.nurseryAddress = nurseryAddress;
    }
}
