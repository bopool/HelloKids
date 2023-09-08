package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.Child;

import java.util.ArrayList;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    Context context;

    ArrayList<Child> childArrayList;

    public ChildAdapter(Context context, ArrayList<Child> childArrayList) {
        this.context = context;
        this.childArrayList = childArrayList;
    }

    @NonNull
    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schoolbus_child,parent,false);
        return new ChildAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder holder, int position) {

        Child child = childArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.textKidsOnBoard.setText(child.getChildName());

    }

    @Override
    public int getItemCount() {
        return childArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textKidsOnBoard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textKidsOnBoard = itemView.findViewById(R.id.textKidsOnBoard);
        }
    }
}
