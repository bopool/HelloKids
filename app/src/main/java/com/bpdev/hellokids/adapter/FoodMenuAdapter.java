package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.FoodmenuEditActivity;
import com.bpdev.hellokids.FoodmenuListActivity;
import com.bpdev.hellokids.FoodmenuViewActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.ScheduleViewActivity;
import com.bpdev.hellokids.api.FoodMenuApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.FoodMenuList;
import com.bpdev.hellokids.model.ScheduleRes;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodMenu> foodMenuArrayList;


    public FoodMenuAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList) {
        this.context = context;
        this.foodMenuArrayList = foodMenuArrayList;
    }


    @NonNull
    @Override
    public FoodMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_foodmenu, parent, false);
        return new FoodMenuAdapter.ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodMenu foodMenu = foodMenuArrayList.get(position);
        Log.i("이미지 출력이 안돼서 테스트 " , "이미지 출력 테스트: position" +position + ", foodMenu.getId(): "+foodMenu.getId() + ", content" + foodMenu.getMealContent() +", "+ foodMenu.getMealDate() +", "+ foodMenu.getMealType());
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoContent;
        TextView textTitle;
        TextView textType;
        CardView foodCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoContent = itemView.findViewById(R.id.photoContent);
            textTitle = itemView.findViewById(R.id.textTitle);
            textType = itemView.findViewById(R.id.textType);
            foodCardView = itemView.findViewById(R.id.foodCardView);

            foodCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int index = getAdapterPosition();

                        Log.i("어댑터 index" , ""+index);
                        FoodMenu foodMenu = foodMenuArrayList.get(index);
                        Intent intent = new Intent(context, FoodmenuViewActivity.class);
                        intent.putExtra("foodMenu",foodMenu);
                        intent.putExtra("index",index);
                        context.startActivity(intent);


                    }
                }
            );

        }
    }
}
