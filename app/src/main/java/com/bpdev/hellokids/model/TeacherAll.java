package com.bpdev.hellokids.model;

import java.io.Serializable;

public class TeacherAll implements Serializable {
    private int id;
    private int classId;
    private int nurseryId;
    private String teacherName;
    private String teacherUserId;
    private String password;
    private String email;
    private String phone;

    public TeacherAll(int classId, int nurseryId, String teacherName, String teacherUserId, String password, String email, String phone) {
        this.classId = classId;
        this.nurseryId = nurseryId;
        this.teacherName = teacherName;
        this.teacherUserId = teacherUserId;
        this.password = password;
        this.email = email;
        this.phone = phone;
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
