package com.bpdev.hellokids.model;

import java.io.Serializable;

public class ClassChild implements Serializable {
    private int classId;
    private String className;
    private int id; // 원아 id
    private String childName;

    public ClassChild(int classId, String className, int id, String childName) {
        this.classId = classId;
        this.className = className;
        this.id = id;
        this.childName = childName;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
