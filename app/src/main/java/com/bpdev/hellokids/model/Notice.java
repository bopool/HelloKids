package com.bpdev.hellokids.model;

import java.io.Serializable;

public class Notice implements Serializable {

    private String noticeTitle;
    private String noticeContent;
    private String[] noticePhotoUrl;
    private int isPublish;

    public Notice(String noticeTitle, String noticeContent, String[] noticePhotoUrl, int isPublish) {
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticePhotoUrl = noticePhotoUrl;
        this.isPublish = isPublish;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String[] getNoticePhotoUrl() {
        return noticePhotoUrl;
    }

    public void setNoticePhotoUrl(String[] noticePhotoUrl) {
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
