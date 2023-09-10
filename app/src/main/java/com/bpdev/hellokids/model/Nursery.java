package com.bpdev.hellokids.model;

public class Nursery {
    private String nurseryName;
    private String nurseryAddress;

    public Nursery(String nurseryName, String nurseryAddress) {
        this.nurseryName = nurseryName;
        this.nurseryAddress = nurseryAddress;
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
