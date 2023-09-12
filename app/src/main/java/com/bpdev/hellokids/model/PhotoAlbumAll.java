package com.bpdev.hellokids.model;

import java.io.Serializable;

public class PhotoAlbumAll  implements Serializable {

    // 사진첩 목록 보기 API에서 response용인 PhotoAlbumAllList과 관련
    public int id;
    public int nurseryId;
    public int classId;
    public int teacherId;
    public int totalAlbumId;
    public String date;
    public String title;
    public String contents;
    public String photoUrl;

    public PhotoAlbumAll(int id, int nurseryId, int classId, int teacherId, int totalAlbumId, String date, String title, String contents, String photoUrl) {
        this.id = id;
        this.nurseryId = nurseryId;
        this.classId = classId;
        this.teacherId = teacherId;
        this.totalAlbumId = totalAlbumId;
        this.date = date;
        this.title = title;
        this.contents = contents;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNurseryId() {
        return nurseryId;
    }

    public void setNurseryId(int nurseryId) {
        this.nurseryId = nurseryId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTotalAlbumId() {
        return totalAlbumId;
    }

    public void setTotalAlbumId(int totalAlbumId) {
        this.totalAlbumId = totalAlbumId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
