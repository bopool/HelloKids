package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.PhotoalbumRecogViewActivity;
import com.bpdev.hellokids.PhotoalbumViewActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.SchoolbusLocationActivity;
import com.bpdev.hellokids.SchoolbusViewActivity;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.PhotoAlbumAll;
import com.bpdev.hellokids.model.PhotoListRes;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class PhotoAlbumRecogAdapter extends RecyclerView.Adapter<PhotoAlbumRecogAdapter.Viewholder> {


    Context context;
    ArrayList<PhotoAlbumAll> photoAlbumResArrayList;


    public PhotoAlbumRecogAdapter(Context context, ArrayList<PhotoAlbumAll> photoAlbumResArrayList) {
        this.context = context;
        this.photoAlbumResArrayList = photoAlbumResArrayList;
    }

    @NonNull
    @Override
    public PhotoAlbumRecogAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_photoalbum_list, parent, false);
        return new PhotoAlbumRecogAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAlbumRecogAdapter.Viewholder holder, int position) {
        // 데이터 가져와서 처리 하는 부분
        PhotoAlbumAll photoAlbumAll = photoAlbumResArrayList.get(position);


        // todo : 레트로핏으로 사진첩의 글 id가 같은 사진의 개수를 세어서 가져오기
        // todo : 가져온 사진 개수 만큼 이미지뷰를 불러서 사진을 세팅하기.
        // todo : 팀장님 말씀이 리사이클러 뷰를 중첩해서 써야 한다고 하심. 이거는 팀장님께서 먼저 하고 계셨으니 계속 진행하고 알려주겠다고 함.

        // 이미지는 글라이드 라이브러리가 처리
//        Glide.with(context).load(photoListRes.imgUrl).into(holder.imgPhoto);
//        Log.i("ADAPTERURL", photoListRes.imgUrl);
//
//        Glide.with(context).load(photoListRes.imgUrl).into(holder.imgPhoto);
//        Log.i("ADAPTERURL", photoListRes.imgUrl);
//
//        Glide.with(context).load(photoListRes.imgUrl).into(holder.imgPhoto);
//        Log.i("ADAPTERURL", photoListRes.imgUrl);
//
//        Glide.with(context).load(photoListRes.imgUrl).into(holder.imgPhoto);
//        Log.i("ADAPTERURL", photoListRes.imgUrl);


        // 텍스트 반영
        holder.textDate.setText( photoAlbumAll.getDate() );
        holder.textTitle.setText( photoAlbumAll.getTitle() );
        holder.textContent.setText( photoAlbumAll.getContents() );
        Glide.with(context)
                .load(photoAlbumAll.getPhotoUrl())
                .into(holder.imgPhoto1);
    }

    @Override
    public int getItemCount() {
        return photoAlbumResArrayList.size();
    }


    public class Viewholder extends  RecyclerView.ViewHolder {

        TextView textDate;
        TextView textTitle;
        TextView textContent;
        Button btnView;
        ImageView imgPhoto1;
        CardView cardView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textDate);
            textTitle = itemView.findViewById(R.id.textTitle);
            textContent = itemView.findViewById(R.id.textContent);
            btnView = itemView.findViewById(R.id.btnView);
            imgPhoto1 = itemView.findViewById(R.id.imgPhoto1);
            cardView = itemView.findViewById(R.id.cardView);

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    PhotoAlbumAll photoAlbumAll =  photoAlbumResArrayList.get(index);
                    int id = photoAlbumAll.getId(); // 사진첩 id
                    Intent intent = new Intent(context, PhotoalbumRecogViewActivity.class);
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }
            });
        }
    }
}

