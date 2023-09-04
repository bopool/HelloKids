package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.ScheduleViewParentsActivity;
import com.bpdev.hellokids.model.ScheduleRes;

import java.util.ArrayList;

public class ScheduleParentsAdapter extends RecyclerView.Adapter<ScheduleParentsAdapter.ViewHolder>{

    Context context;

    ArrayList<ScheduleRes> scheduleArrayList;

    int id;

    public ScheduleParentsAdapter(Context context, ArrayList<ScheduleRes> scheduleArrayList) {
        this.context = context;
        this.scheduleArrayList = scheduleArrayList;
    }

    @NonNull
    @Override
    public ScheduleParentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schedule,parent,false);
        return new ScheduleParentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleParentsAdapter.ViewHolder holder, int position) {
        ScheduleRes schedule = scheduleArrayList.get(position); // position는 해당 위치를 나타낸다

        int selectIcon = schedule.getSelectIcon();

        Log.i("selectIcon",selectIcon+"");

        holder.noticeTitle.setText(schedule.getTitle());
        holder.noticeContents.setText(schedule.getContents());
        holder.noticeDate.setText(schedule.getDate());

        if (selectIcon == 1){
            holder.imgDate.setImageResource(R.drawable.icon02);
        } else if(selectIcon == 2){
            holder.imgDate.setImageResource(R.drawable.icon10);
        }else if(selectIcon == 3){
            holder.imgDate.setImageResource(R.drawable.icon03);
        }else if(selectIcon == 4){
            holder.imgDate.setImageResource(R.drawable.icon06);
        }else if(selectIcon == 5){
            holder.imgDate.setImageResource(R.drawable.icon01);
        }else{
            holder.imgDate.setImageResource(R.drawable.icon13);
        }


    }

    @Override
    public int getItemCount() {
        return scheduleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView noticeTitle;
        TextView noticeContents;

        TextView noticeDate;
        ImageView imgDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeContents = itemView.findViewById(R.id.noticeContents);
            noticeDate = itemView.findViewById(R.id.noticeDate);
            imgDate = itemView.findViewById(R.id.imgDate);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    ScheduleRes schedule = scheduleArrayList.get(index);
                    Intent intent = new Intent(context, ScheduleViewParentsActivity.class);
                    intent.putExtra("schedule",schedule);
                    intent.putExtra("index",index);
                    Log.i("scheduleId",schedule.getId()+"");
                    Log.i("index",index+"");
                    context.startActivity(intent);
                }
            });

        }

    }
}
