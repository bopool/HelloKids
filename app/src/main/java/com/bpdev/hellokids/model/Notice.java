package com.bpdev.hellokids.model;

import java.io.Serializable;

public class Notice implements Serializable {

    private int id;
    private String noticeDate;
    private String noticeTitle;
    private String noticeContents;
    private String noticePhotoUrl;
    private int isPublish;

    public Notice(int id, String noticeDate, String noticeTitle, String noticeContents, String noticePhotoUrl, int isPublish) {
        this.id = id;
        this.noticeDate = noticeDate;
        this.noticeTitle = noticeTitle;
        this.noticeContents = noticeContents;
        this.noticePhotoUrl = noticePhotoUrl;
        this.isPublish = isPublish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContents() {
        return noticeContents;
    }

    public void setNoticeContents(String noticeContents) {
        this.noticeContents = noticeContents;
    }

    public String getNoticePhotoUrl() {
        return noticePhotoUrl;
    }

    public void setNoticePhotoUrl(String noticePhotoUrl) {
        this.noticePhotoUrl = noticePhotoUrl;
    }

    public int getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(int isPublish) {
        this.isPublish = isPublish;
    }

    public Notice() {
    }
}
