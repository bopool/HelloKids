package com.bpdev.hellokids.model;

public class User {
    // 학부모,선생님 둘 다 공통으로 사용한다
    private String teacherName;
    private String id;
    private String password;
    private String email;
    private String phone;

    public User(String teacherName, String id, String password, String email, String phone) {
        this.teacherName = teacherName;
        this.id = id;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
