package com.bpdev.hellokids.model;

import java.io.Serializable;

public class Parents implements Serializable {
    private int id;
    private int childId;
    private String nurseryName;
    private String parentsName;
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String childNameP;
    private String birthP;

    public String getChildNameP() {
        return childNameP;
    }

    public void setChildNameP(String childNameP) {
        this.childNameP = childNameP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public String getParentsName() {
        return parentsName;
    }

    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthP() {
        return birthP;
    }

    public void setBirthP(String birthP) {
        this.birthP = birthP;
    }
}
