package com.bpdev.hellokids.model;

import java.io.Serializable;

public class nurseryClass implements Serializable {

    private String className;

    public nurseryClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
