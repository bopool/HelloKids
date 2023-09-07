package com.bpdev.hellokids.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.FoodmenuEditActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.FoodMenu;

public class HorizonViewHolder extends RecyclerView.ViewHolder {
    TextView textDate;
    RecyclerView subRecyclerView;
    public HorizonViewHolder(View itemView) {
        super(itemView);
        textDate = itemView.findViewById(R.id.textDate);
        subRecyclerView = itemView.findViewById(R.id.subRecyclerView);
    }
}