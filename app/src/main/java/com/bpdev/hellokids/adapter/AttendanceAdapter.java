package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.AttendanceEditActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.SchoolbusInfoActivity;
import com.bpdev.hellokids.model.AttendanceRes;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.ClassChild;
import com.bpdev.hellokids.model.DailyNoteRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        String date = attendanceRes.getDate().substring(0,4)+"년"+attendanceRes.getDate().substring(5,7)+"월"+attendanceRes.getDate().substring(8,10)+"일";

        holder.childName.setText(attendanceRes.getChildName());
        holder.applyDate.setText(date);
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
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.childName);
            applyDate = itemView.findViewById(R.id.applyDate);
            textCheck = itemView.findViewById(R.id.textCheck);
            textMemo = itemView.findViewById(R.id.textMemo);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    AttendanceRes attendanceRes = attendanceArrayList.get(index);
                    Intent intent = new Intent(context, AttendanceEditActivity.class);
                    intent.putExtra("attendanceRes",attendanceRes);
                    context.startActivity(intent);
                }
            });
        }
    }
}
