package com.bpdev.hellokids.model;

import java.io.Serializable;

public class NurseryClass implements Serializable {

    private int id;
    private String className;

    public NurseryClass(int id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}