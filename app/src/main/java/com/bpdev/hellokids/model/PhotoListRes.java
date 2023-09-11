package com.bpdev.hellokids.model;

import java.io.Serializable;

public class PhotoListRes implements Serializable {

    // 사진첩 목록 보기 API 응답 받는 클래스 PhotoList 에서 items를 받는 클래스.

    public int classId;
    public int teacherId;
    public int totalAlbumId;
    public String date;
    public String title;
    public String contents;
    public String photoUrl;


    public PhotoListRes(int classId, int teacherId, int totalAlbumId, String date, String title, String contents, String photoUrl) {
        this.classId = classId;
        this.teacherId = teacherId;
        this.totalAlbumId = totalAlbumId;
        this.date = date;
        this.title = title;
        this.contents = contents;
        this.photoUrl = photoUrl;
    }

    public PhotoListRes() {
    }

}
