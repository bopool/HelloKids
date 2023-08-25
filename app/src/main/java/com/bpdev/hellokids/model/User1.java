package com.bpdev.hellokids.model;

public class User1 {
    // 학부모 사용
    private String parentsName;
    private String userId;
    private String password;
    private String email;
    private String phone;

    private String childNameP;
    private String birthP;

    public User1(String parentsName, String userId, String password, String email, String phone, String childNameP, String birthP) {
        this.parentsName = parentsName;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.childNameP = childNameP;
        this.birthP = birthP;
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

    public String getChildNameP() {
        return childNameP;
    }

    public void setChildNameP(String childNameP) {
        this.childNameP = childNameP;
    }

    public String getBirthP() {
        return birthP;
    }

    public void setBirthP(String birthP) {
        this.birthP = birthP;
    }
}
