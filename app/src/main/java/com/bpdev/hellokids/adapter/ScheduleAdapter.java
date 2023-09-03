package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.Schedule;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    Context context;

    ArrayList<Schedule> scheduleArrayList;

    public ScheduleAdapter(Context context, ArrayList<Schedule> scheduleArrayList) {
        this.context = context;
        this.scheduleArrayList = scheduleArrayList;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schedule,parent,false);
        return new ScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {
        Schedule schedule = scheduleArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.noticeTitle.setText(schedule.getTitle());
        holder.noticeContents.setText(schedule.getContents());

    }

    @Override
    public int getItemCount() {
        return scheduleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView noticeTitle;
        TextView noticeContents;

        TextView noticeCheck;
        ImageView imgDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeContents = itemView.findViewById(R.id.noticeContents);
            noticeCheck = itemView.findViewById(R.id.noticeCheck);
            imgDate = itemView.findViewById(R.id.imgDate);

        }
    }
}
