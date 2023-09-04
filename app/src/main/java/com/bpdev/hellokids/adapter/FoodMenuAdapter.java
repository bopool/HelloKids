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
import com.bpdev.hellokids.model.FoodMenu;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodMenu> foodMenuArrayList;

    public FoodMenuAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList) {
        this.context = context;
        this.foodMenuArrayList = foodMenuArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_foodmenu, parent, false);
        return new FoodMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodMenu foodMenu = foodMenuArrayList.get(position);

        Glide.with(context)
                .load(foodMenu.getMealPhotoUrl())
                .into(holder.photoContent);

        holder.textTitle.setText(foodMenu.getMealContent() );
        holder.textType.setText(foodMenu.getMealType());
    }

    @Override
    public int getItemCount() {
        return foodMenuArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoContent;
        TextView textTitle;
        TextView textType;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoContent = itemView.findViewById(R.id.photoContent);
            textTitle = itemView.findViewById(R.id.textTitle);
            textType = itemView.findViewById(R.id.textType);




        }
    }
}
