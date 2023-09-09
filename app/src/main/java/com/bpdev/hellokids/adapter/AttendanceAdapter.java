package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.AttendanceRes;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.ClassChild;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder>{

    Context context;

    ArrayList<AttendanceRes> attendanceArrayList;

    public AttendanceAdapter(Context context, ArrayList<AttendanceRes> attendanceArrayList) {
        this.context = context;
        this.attendanceArrayList = attendanceArrayList;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attendance_childlist,parent,false);
        return new AttendanceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        AttendanceRes attendanceRes = attendanceArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.childName.setText(attendanceRes.getChildName());
        holder.applyDate.setText(attendanceRes.getDate());
        holder.textCheck.setText(attendanceRes.getStatus());
        holder.textMemo.setText(attendanceRes.getMemo());

    }

    @Override
    public int getItemCount() {
        return attendanceArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView childName;
        TextView applyDate;
        TextView textCheck;
        TextView textMemo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.childName);
            applyDate = itemView.findViewById(R.id.applyDate);
            textCheck = itemView.findViewById(R.id.textCheck);
            textMemo = itemView.findViewById(R.id.textMemo);
        }
    }
}
