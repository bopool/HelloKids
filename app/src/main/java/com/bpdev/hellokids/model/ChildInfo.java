package com.bpdev.hellokids.model;

import java.io.Serializable;

public class ChildInfo implements Serializable {
    private int id;
    private int classId;
    private int nurseryId;
    private String childName;
    private String birth;
    private int sex;
    private String profileUrl;

    public ChildInfo(String childName, String birth, int sex) {
        this.childName = childName;
        this.birth = birth;
        this.sex = sex;
    }

    public ChildInfo(int id, int classId, String childName, String profileUrl) {
        this.id = id;
        this.classId = classId;
        this.childName = childName;
        this.profileUrl = profileUrl;
    }

    public ChildInfo(int id, int classId, int nurseryId, String childName, String birth, int sex, String profileUrl) {
        this.id = id;
        this.classId = classId;
        this.nurseryId = nurseryId;
        this.childName = childName;
        this.birth = birth;
        this.sex = sex;
        this.profileUrl = profileUrl;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getNurseryId() {
        return nurseryId;
    }

    public void setNurseryId(int nurseryId) {
        this.nurseryId = nurseryId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
