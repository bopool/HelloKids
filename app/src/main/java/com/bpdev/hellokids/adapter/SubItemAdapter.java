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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


// 자식 어답터
public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private List<FoodMenu> subItemList;
    Context context;
    ArrayList<FoodMenu> foodMenuArrayList;


    SubItemAdapter(List<FoodMenu> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_foodmenu, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        FoodMenu foodMenu = foodMenuArrayList.get(i);
        Log.i("이미지 출력이 안돼서 테스트 " , "이미지 출력 테스트" + foodMenu.getMealPhotoUrl());
        subItemViewHolder.textTitle.setText(foodMenu.getMealContent() );
        subItemViewHolder.textType.setText(foodMenu.getMealType());
        Glide.with(context)
                .load(foodMenu.getMealPhotoUrl())
                .into(subItemViewHolder.photoContent);
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        ImageView photoContent;
        TextView textTitle;
        TextView textType;
        CardView foodCardView;


        SubItemViewHolder(View itemView) {
            super(itemView);

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
                                            }
            );
        }
    }
}
