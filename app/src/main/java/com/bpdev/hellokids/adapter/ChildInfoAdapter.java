package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildInfo;
import com.bpdev.hellokids.model.Teacher;

import java.util.ArrayList;

public class ChildInfoAdapter extends RecyclerView.Adapter<ChildInfoAdapter.ViewHolder> {

    Context context;

    ArrayList<ChildInfo> childInfoArrayList;

    public ChildInfoAdapter(Context context, ArrayList<ChildInfo> childInfoArrayList) {
        this.context = context;
        this.childInfoArrayList = childInfoArrayList;
    }

    @NonNull
    @Override
    public ChildInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_setting_childinfo,parent,false);
        return new ChildInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildInfoAdapter.ViewHolder holder, int position) {
        ChildInfo childInfo = childInfoArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.textChildName.setText(childInfo.getChildName());
        holder.textBirthDate.setText(childInfo.getBirth());
    }

    @Override
    public int getItemCount() {
        return childInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textChildName;
        TextView textBirthDate;
        ImageView imgKidsProfile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textChildName = itemView.findViewById(R.id.textChildName);
            textBirthDate = itemView.findViewById(R.id.textBirthDate);
            imgKidsProfile = itemView.findViewById(R.id.imgKidsProfile);
        }
    }
}
