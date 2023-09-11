package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class PhotoAlbumRes {

        int nurseryId;
        int classId;
        int teacherId;
        String date;
        String title;
        String contents;

        String photoUrl;

        //ArrayList<PhotoAlbumResUrlList> photoAlbumResUrlList;

        public PhotoAlbumRes(int nurseryId, int classId, int teacherId, String date, String title, String contents, ArrayList<PhotoAlbumResUrlList> photoAlbumResUrlList) {
                this.nurseryId = nurseryId;
                this.classId = classId;
                this.teacherId = teacherId;
                this.date = date;
                this.title = title;
                this.contents = contents;
                //this.photoAlbumResUrlList = photoAlbumResUrlList;
        }

        public PhotoAlbumRes() {
        }

}
