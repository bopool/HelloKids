package com.bpdev.hellokids.adapter;


import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.PhotoList;
import com.bpdev.hellokids.model.PhotoListRes;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.Viewholder>{


    Context context;
    ArrayList<PhotoListRes> photoListResArrayList;

    public PhotoListAdapter(Context context, ArrayList<PhotoListRes> photoListResArrayList) {
        this.context = context;
        this.photoListResArrayList = photoListResArrayList;
    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_photoalbum_list, parent, false);
        return new PhotoListAdapter.Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // 데이터 가져와서 처리 하는 부분
        PhotoListRes photoListRes = photoListResArrayList.get(position);


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
        holder.textDate.setText( photoListRes.date );
        holder.textTitle.setText( photoListRes.title );
        holder.textContent.setText( photoListRes.contents );
    }


    @Override
    public int getItemCount() {
        return photoListResArrayList.size();
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

            //
            textDate = itemView.findViewById(R.id.textDate);
            textTitle = itemView.findViewById(R.id.textTitle);
            textContent = itemView.findViewById(R.id.textContent);
            btnView = itemView.findViewById(R.id.btnView);
            imgPhoto1 = itemView.findViewById(R.id.imgPhoto1);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }

}
