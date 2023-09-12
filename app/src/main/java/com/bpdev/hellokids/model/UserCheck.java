package com.bpdev.hellokids.model;

public class UserCheck {

    private int isTeacher;

    public UserCheck() {
    }

    public UserCheck(int isTeacher) {
        this.isTeacher = isTeacher;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }
}
