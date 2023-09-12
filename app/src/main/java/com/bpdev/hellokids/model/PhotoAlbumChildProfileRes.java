package com.bpdev.hellokids.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoAlbumChildProfileRes implements Serializable {

    // 포토앨범 레코그 액티비티에서 원아 프로필 사진 받는 클래스
    String result;
    String profileUrl;




    public PhotoAlbumChildProfileRes(String result, String profileUrl) {
        this.result = result;
        this.profileUrl = profileUrl;
    }

    public PhotoAlbumChildProfileRes() {
    }


}
