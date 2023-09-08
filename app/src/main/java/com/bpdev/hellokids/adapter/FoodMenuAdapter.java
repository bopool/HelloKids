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
import com.bpdev.hellokids.FoodmenuListActivity;
import com.bpdev.hellokids.FoodmenuViewActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.ScheduleViewActivity;
import com.bpdev.hellokids.model.FoodMenu;
import com.bpdev.hellokids.model.ScheduleRes;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodMenu> foodMenuArrayList;


    public FoodMenuAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList) {
        this.context = context;
        this.foodMenuArrayList = foodMenuArrayList;
    }


//    public ActivityResultLauncher<Intent> launcher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                    new ActivityResultCallback<ActivityResult>() {
//                        @Override // ActivityResult가 있다면 동작하라.
//                        public void onActivityResult(ActivityResult result) {
//
//                            // Add Activity로 부터 데이터를 받는 경우
//                            if( result.getResultCode() == 1 ){
//
//                                Employee employee = (Employee) result.getData().getSerializableExtra("employee"); // 보낸 데이터들 불러오기
//                                employeeArrayList.add(0, employee); // 목록에 추가
//                                adapter.notifyDataSetChanged(); // 화면 갱신
//
//                            } else if( result.getResultCode() == 2 ){
//                                Employee employee = (Employee) result.getData().getSerializableExtra("employee"); // 보낸 데이터들 불러오기
//                                int index = result.getData().getIntExtra("index", 0); // 보낸 인덱스 데이터도 불러오기
//                                employeeArrayList.set(index, employee); // 이 인덱스 데이터 업데이트 해주세요!
//                                adapter.notifyDataSetChanged(); // 화면 갱신
//
//                            }
//                        }
//                    });

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
//                        ((FoodmenuListActivity)context).launcher.launch(intent);

                        int index = getAdapterPosition();
                        FoodMenu foodMenu = foodMenuArrayList.get(index);
                        Intent intent = new Intent(context, FoodmenuViewActivity.class);
                        intent.putExtra("foodMenu",foodMenu);
                        intent.putExtra("index",index);
                        Log.i("scheduleId",foodMenu.getMealDate()+"");
                        Log.i("index",index+"");
                        context.startActivity(intent);


                    }
                }
            );

        }
    }
}
