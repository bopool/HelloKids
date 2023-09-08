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

import com.bpdev.hellokids.FoodmenuEditActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.FoodMenuDayList;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodMenuAllAdapter extends RecyclerView.Adapter<FoodMenuAllAdapter.FoodMenuViewHolder> {

    Context context;
    ArrayList<FoodMenu> foodMenuArrayList;
    LayoutInflater inflater;

    public FoodMenuAllAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList) {
        this.context = context;
        this.foodMenuArrayList = foodMenuArrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FoodMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.row_foodmenu, parent, false);
        return new FoodMenuViewHolder(view);
    }

     @Override
    public void onBindViewHolder(@NonNull FoodMenuViewHolder holder, int position) {
        FoodMenu foodMenu = foodMenuArrayList.get(position);
        Log.i("이미지 출력이 안돼서 테스트 " , "이미지 출력 테스트" + foodMenu.getMealPhotoUrl());
        holder.textTitle.setText(foodMenu.getMealContent() );
        holder.textType.setText(foodMenu.getMealType());
        Glide.with(context)
                .load(foodMenu.getMealPhotoUrl())
                .into(holder.photoContent);
    }

    @Override
    public int getItemCount() {
        return foodMenuArrayList.size();
    }


    class FoodMenuViewHolder extends RecyclerView.ViewHolder {
        ImageView photoContent;
        TextView textTitle;
        TextView textType;
        CardView foodCardView;

        FoodMenuViewHolder(View foodMenuView) {
            super(foodMenuView);


            photoContent = itemView.findViewById(R.id.photoContent);
            textTitle = itemView.findViewById(R.id.textTitle);
            textType = itemView.findViewById(R.id.textType);
            foodCardView = itemView.findViewById(R.id.foodCardView);

            foodCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    FoodMenu foodMenu = foodMenuArrayList.get(index);
                    Intent intent = new Intent(context, FoodmenuEditActivity.class);
                    intent.putExtra("index", index);

                    // context가 메인액티비티라고 알려주고, 메인액티비티에서 launcher는 public으로 세팅해 준다.
//                        ((FoodmenuListActivity)context).launcher.launch(intent);

                }
            });

        }
    }
}

//
//    Context context;
//    ArrayList<FoodMenu> foodMenuArrayList;
//
//    public FoodMenuAllAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList) {
//        this.context = context;
//        this.foodMenuArrayList = foodMenuArrayList;
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_foodmenu, parent, false);
//        return new FoodMenuAllAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        FoodMenu foodMenu = foodMenuArrayList.get(position);
//
//        Glide.with(context)
//                .load(foodMenu.getMealPhotoUrl())
//                .into(holder.photoContent);
//        holder.textTitle.setText(foodMenu.getMealContent() );
//        holder.textType.setText(foodMenu.getMealType());
//    }
//
//    @Override
//    public int getFoodMenuCount() {
//        return foodMenuArrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView photoContent;
//        TextView textTitle;
//        TextView textType;
//
//
//        public ViewHolder(@NonNull View foodMenuView) {
//            super(foodMenuView);
//
//            photoContent = foodMenuView.findViewById(R.id.photoContent);
//            textTitle = foodMenuView.findViewById(R.id.textTitle);
//            textType = foodMenuView.findViewById(R.id.textType);
//
//
//
//        }
//    }
//}
