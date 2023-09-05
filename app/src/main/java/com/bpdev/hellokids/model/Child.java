package com.bpdev.hellokids.model;

import java.io.Serializable;

public class Child implements Serializable {
    private int id;
    private String childName;

    public Child(int id, String childName) {
        this.id = id;
        this.childName = childName;
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
}
