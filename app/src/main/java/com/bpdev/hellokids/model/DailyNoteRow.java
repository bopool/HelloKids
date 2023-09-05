package com.bpdev.hellokids.model;

import java.io.Serializable;

public class DailyNoteRow implements Serializable {

    private int id;
    private String createdAt;
    private String title;
    private String contents;

    private String dailyTemperCheck;
    private String dailyMealCheck;
    private String dailyNapCheck;
    private String dailyPooCheck;

    public DailyNoteRow(int id, String createdAt, String title, String contents, String dailyTemperCheck, String dailyMealCheck, String dailyNapCheck, String dailyPooCheck) {
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.contents = contents;
        this.dailyTemperCheck = dailyTemperCheck;
        this.dailyMealCheck = dailyMealCheck;
        this.dailyNapCheck = dailyNapCheck;
        this.dailyPooCheck = dailyPooCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDailyTemperCheck() {
        return dailyTemperCheck;
    }

    public void setDailyTemperCheck(String dailyTemperCheck) {
        this.dailyTemperCheck = dailyTemperCheck;
    }

    public String getDailyMealCheck() {
        return dailyMealCheck;
    }

    public void setDailyMealCheck(String dailyMealCheck) {
        this.dailyMealCheck = dailyMealCheck;
    }

    public String getDailyNapCheck() {
        return dailyNapCheck;
    }

    public void setDailyNapCheck(String dailyNapCheck) {
        this.dailyNapCheck = dailyNapCheck;
    }

    public String getDailyPooCheck() {
        return dailyPooCheck;
    }

    public void setDailyPooCheck(String dailyPooCheck) {
        this.dailyPooCheck = dailyPooCheck;
    }
}
