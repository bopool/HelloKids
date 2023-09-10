package com.bpdev.hellokids.model;

public class User {
    // 선생님 사용
    private String teacherName;
    private String teacherUserId;
    private String password;
    private String email;
    private String phone;

    public User(String teacherName, String teacherUserId, String password, String email, String phone) {
        this.teacherName = teacherName;
        this.teacherUserId = teacherUserId;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherUserId() {
        return teacherUserId;
    }

    public void setTeacherUserId(String teacherUserId) {
        this.teacherUserId = teacherUserId;
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
}
