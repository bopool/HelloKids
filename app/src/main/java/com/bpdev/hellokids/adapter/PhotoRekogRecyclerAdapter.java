package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.PhotoalbumRekogActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.SettingChildProfileActivity;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildInfo;
import com.bpdev.hellokids.model.ChildList;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PhotoRekogRecyclerAdapter extends RecyclerView.Adapter<PhotoRekogRecyclerAdapter.ViewHolder>{
    
    // 얼굴인식 사진첩 추가 화면에서 원아 프로필 불러오는 리사이클러뷰와 연결

    Context context;
    ArrayList<Child> childArrayList;


    int childId1;

    public PhotoRekogRecyclerAdapter(Context context, ArrayList<Child> childArrayList) {
        this.context = context;
        this.childArrayList = childArrayList;
    }





    @NonNull
    @Override
    public PhotoRekogRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photoalbum_add_childprofile, parent, false);
        return new PhotoRekogRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoRekogRecyclerAdapter.ViewHolder holder, int position) {

        Child child = childArrayList.get(position); // position는 해당 위치를 나타낸다

        childId1 = child.getId();

        holder.textChildName.setText(child.getChildName());
        // holder.textBirthDate.setText(child.getBirth());
//        Glide.with(context)
//                .load(childInfo.getProfileUrl())
//                .into(holder.imgKidsProfile);

    }

    @Override
    public int getItemCount() {
        return 0;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        //ImageView imgKidsProfile;
        TextView textChildName;
        //TextView textBirthDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            //imgKidsProfile = itemView.findViewById(R.id.imgKidsProfile);
            textChildName = itemView.findViewById(R.id.textChildName);
            //textBirthDate = itemView.findViewById(R.id.textBirthDate);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PhotoalbumRekogActivity.class);
                    intent.putExtra("childId1",childId1);
                    context.startActivity(intent);
                }
            });


        }
    }


}
