package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.NoticeListActivity;
import com.bpdev.hellokids.PhotoalbumRekogActivity;
import com.bpdev.hellokids.PhotoalbumRekogRecyclerActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.SettingChildProfileActivity;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildInfo;
import com.bpdev.hellokids.model.ChildList;
import com.bpdev.hellokids.model.NetworkClient;
import com.bpdev.hellokids.model.Notice;
import com.bpdev.hellokids.model.PhotoAlbumAll;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoRekogRecyclerAdapter extends RecyclerView.Adapter<PhotoRekogRecyclerAdapter.ViewHolder>{
    
    // 얼굴인식 사진첩 추가 화면에서 원아 프로필 불러오는 리사이클러뷰와 연결

    Context context;
    ArrayList<ChildInfo> childInfoArrayList;
    ChildInfo childInfo;


    int id;


    public PhotoRekogRecyclerAdapter(Context context, ArrayList<ChildInfo> childInfoArrayList) {
        this.context = context;
        this.childInfoArrayList = childInfoArrayList;
    }


    @NonNull
    @Override
    public PhotoRekogRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_setting_childinfo, parent, false);
        return new PhotoRekogRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        childInfo = childInfoArrayList.get(position); // position는 해당 위치를 나타낸다
        id = childInfo.getId();

        holder.textChildName.setText(childInfo.getChildName());
        holder.textBirthDate.setText(childInfo.getBirth());
        Glide.with(context)
                .load(childInfo.getProfileUrl())
                .into(holder.imgKidsProfile);

    }


    @Override
    public int getItemCount() {
        return childInfoArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textChildName;
        TextView textBirthDate;
        ImageView imgKidsProfile;
        CardView cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textChildName = itemView.findViewById(R.id.textChildName);
            textBirthDate = itemView.findViewById(R.id.textBirthDate);
            imgKidsProfile = itemView.findViewById(R.id.imgKidsProfile);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int index = getAdapterPosition();
                    childInfo =  childInfoArrayList.get(index);
                    Intent intent = new Intent(context, PhotoalbumRekogActivity.class);
                    int id = childInfo.getId(); // 원아 아이디

                    intent.putExtra("id", id);
                    String profileUrl = childInfo.getProfileUrl();// 원아 프로필 사진
                    intent.putExtra("profileUrl", profileUrl);

//                    Intent intent1 = new Intent(context, PhotoalbumRekogActivity.class);
//                    String profileUrl = childInfo.getProfileUrl();// 원아 프로필 사진
//                    intent1.putExtra("profileUrl", profileUrl);



                    context.startActivity(intent);
                }
            });
        }


    }


}
