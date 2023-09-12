package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.PhotoAlbumAll;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PhotoViewAdapter extends RecyclerView.Adapter< PhotoViewAdapter.Viewholder>{

    Context context;
    ArrayList<PhotoAlbumAll> photoAlbumResArrayList;

    public PhotoViewAdapter(Context context, ArrayList<PhotoAlbumAll> photoAlbumResArrayList) {
        this.context = context;
        this.photoAlbumResArrayList = photoAlbumResArrayList;
    }

    @NonNull
    @Override
    public PhotoViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_photoalbum_view, parent, false);
        return new PhotoViewAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewAdapter.Viewholder holder, int position) {
        PhotoAlbumAll photoAlbumAll = photoAlbumResArrayList.get(position);

        Glide.with(context)
                .load(photoAlbumAll.getPhotoUrl())
                .into(holder.photoContent0);
    }

    @Override
    public int getItemCount() {
        return photoAlbumResArrayList.size();
    }

    public class Viewholder extends  RecyclerView.ViewHolder {
        ImageView photoContent0;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            photoContent0 = itemView.findViewById(R.id.photoContent0);
        }
    }
}
