package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.Child;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChildTimeAdapter extends RecyclerView.Adapter<ChildTimeAdapter.ViewHolder>  {
    Context context;

    ArrayList<Child> childArrayList;
    
    String shuttleIn = "탑승시간";
    String shuttleOut = "하차시간";

    public ChildTimeAdapter(Context context, ArrayList<Child> childArrayList) {
        this.context = context;
        this.childArrayList = childArrayList;
    }

    @NonNull
    @Override
    public ChildTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schoolbus_info_child_onboard,parent,false);
        return new ChildTimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildTimeAdapter.ViewHolder holder, int position) {

        Child child = childArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.textChildName.setText(child.getChildName());

        holder.checkShuttleIn.setOnClickListener(v -> {
            if(holder.checkShuttleIn.isChecked()) {
                Date today = new Date();
                Locale currentLocale = new Locale("KOREAN", "KOREA");
                String pattern = "HH:mm"; //hhmmss로 시간,분,초만 뽑기도 가능
                SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
                shuttleIn =formatter.format(today);
                holder.textOffTime.setText(shuttleIn);
            }
            else {
                // 체크가 되어있지 않음
                //mCheckBoxClickListener.onClickCheckBox(0, position);
            }
        });
        holder.checkShuttleOut.setOnClickListener(v -> {
            if(holder.checkShuttleOut.isChecked()) {
                Date today1 = new Date();
                Locale currentLocale1 = new Locale("KOREAN", "KOREA");
                String pattern1 = "HH:mm"; //hhmmss로 시간,분,초만 뽑기도 가능
                SimpleDateFormat formatter1 = new SimpleDateFormat(pattern1, currentLocale1);
                shuttleOut =formatter1.format(today1);
                holder.textOnTime.setText(shuttleOut);
            }
            else {
                // 체크가 되어있지 않음
                //mCheckBoxClickListener.onClickCheckBox(0, position);
            }
        });

        holder.textOnTime.setText(shuttleIn);
        holder.textOffTime.setText(shuttleOut);

    }

    @Override
    public int getItemCount() {
        return childArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textChildName;
        CheckBox checkShuttleIn;
        CheckBox checkShuttleOut;
        TextView textOnTime;
        TextView textOffTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textChildName = itemView.findViewById(R.id.textChildName);
            checkShuttleIn = itemView.findViewById(R.id.checkShuttleOut);
            checkShuttleOut = itemView.findViewById(R.id.checkShuttleIn);
            textOnTime = itemView.findViewById(R.id.textOnTime);
            textOffTime = itemView.findViewById(R.id.textOffTime);
        }
    }
}
