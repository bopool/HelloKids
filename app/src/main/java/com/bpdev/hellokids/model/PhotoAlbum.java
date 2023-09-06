package com.bpdev.hellokids.model;


import android.net.Uri;

import java.util.ArrayList;

public class PhotoAlbum {

    // public String photoUrl;
    public String date;
    public String title;
    public String contents;
    public int classId;

    public String[] photoUrl;


    public ArrayList<Uri> uriList;



    public PhotoAlbum(String date, String title, String contents, int classId, ArrayList<Uri> uriList) {
        this.date = date;
        this.title = title;
        this.contents = contents;
        this.classId = classId;



        this.uriList = uriList;
    }




    public PhotoAlbum() {
    }


    public PhotoAlbum(String date, String title, String contents, int classId, String[] photoUrl) {
        this.date = date;
        this.title = title;
        this.contents = contents;
        this.classId = classId;
        this.photoUrl = photoUrl;
    }
}
