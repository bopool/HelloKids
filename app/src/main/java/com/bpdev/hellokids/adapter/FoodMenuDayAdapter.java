package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.FoodMenu;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;



public class FoodMenuDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<FoodMenu> foodMenuArrayList;
    LayoutInflater inflater;

    public FoodMenuDayAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList, LayoutInflater inflater) {
        this.context = context;
        this.foodMenuArrayList = foodMenuArrayList;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//
//        if (viewType == Constant.CHAPTER_VIEW) {
//            view = inflater.inflate(R.layout.single_subject, parent, false);
//            return new HorizonViewHolder(view);
//        } else {
//            view = inflater.inflate(R.layout.single_subject, parent, false);
//            return new GridViewHolder(view);
//        }
//
//    }
//
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof HorizonViewHolder)
//        {
//            ((HorizonViewHolder) holder).recyclerView.setAdapter(new FoodMenuAllAdapter(context, subjects.get(position).chapters));
//            ((HorizonViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//            ((HorizonViewHolder) holder).recyclerView.setHasFixedSize(true);
//            ((HorizonViewHolder) holder).tvSubjectName.setText(subjects.get(position).subjectName);
//        }
//        else
//        {
//            ((GridViewHolder) holder).recyclerView.setAdapter(new ChapterAdapter(context, subjects.get(position).chapters));
//            ((GridViewHolder) holder).recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
//            ((GridViewHolder) holder).recyclerView.setHasFixedSize(true);
//            ((GridViewHolder) holder).tvSubjectName.setText(subjects.get(position).subjectName);
//        }
//
//    @Override
//    public int getItemCount() {
//        return subItemList.size();
//    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView textDate;
        RecyclerView subRecyclerView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textDate);
            subRecyclerView = itemView.findViewById(R.id.subRecyclerView);


        }
    }
}





//
//
//public class FoodMenuDayAdapter {
//
//    private List<FoodMenu> subItemList;
//
//    FoodMenuDayAdapter(List<FoodMenu> subItemList) {
//        this.subItemList = subItemList;
//    }
//
//    @NonNull
//    @Override
//    public FoodMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_foodmenu, viewGroup, false);
//        return new FoodMenuViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FoodMenuViewHolder subItemViewHolder, int i) {
//        FoodMenu subItem = subItemList.get(i);
//        subItemViewHolder.tvFoodMenuTitle.setText(subItem.getFoodMenuTitle());
//    }
//
//    @Override
//    public int getItemCount() {
//        return subItemList.size();
//    }
//
//    class FoodMenuViewHolder extends RecyclerView.ViewHolder {
//        TextView tvFoodMenuTitle;
//
//        FoodMenuViewHolder(View itemView) {
//            super(itemView);
//            tvFoodMenuTitle = itemView.findViewById(R.id.tv_sub_item_title);
//        }
//    }
//}

//
//    Context context;
//    ArrayList<FoodMenuDayList> foodMenuDayListArrayList;
//
//    public FoodMenuDayAdapter(Context context, ArrayList<FoodMenuDayList> foodMenuDayListArrayList) {
//        this.context = context;
//        this.foodMenuDayListArrayList = foodMenuDayListArrayList;
//    }
//
////
////    @NonNull
////    @Override
////    public FoodMenuDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext())
////                .inflate(R.layout.row_subrecyclerview, parent, false);
////        return new FoodMenuDayAdapter.ViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull FoodMenuDayAdapter.ViewHolder holder, int position) {
////        FoodMenuDayList foodMenuDayList = foodMenuDayListArrayList.get(position);
//////        holder.textDate.setText(foodMenuDayListArrayList.textDate);
////
//////        holder.recyclerViewH.(foodMenu.getMealType());
////    }
////
////    @Override
////    public int getItemCount() {
////        return foodMenuDayListArrayList.size();
////    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//
//        TextView textDate;
//        RecyclerView recyclerViewH;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            textDate = itemView.findViewById(R.id.textDate);
//            recyclerViewH = itemView.findViewById(R.id.recyclerViewH);
//
//
//        }
//    }
//}
