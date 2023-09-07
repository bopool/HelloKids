package com.bpdev.hellokids.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;

public class GridViewHolder extends RecyclerView.ViewHolder {
    TextView textDate;
    RecyclerView subRecyclerView;

    public GridViewHolder(View itemView) {
        super(itemView);
        textDate = itemView.findViewById(R.id.textDate);
        subRecyclerView = itemView.findViewById(R.id.subRecyclerView);
    }
}