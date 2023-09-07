//package com.bpdev.hellokids.adapter;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bpdev.hellokids.R;
//import com.bpdev.hellokids.model.FoodMenu;
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//
//
//// 자식 어답터
//public class FoodMenuDayAdapter extends RecyclerView.Adapter<FoodMenuDayAdapter.SubItemViewHolder> {
//
//    private List<FoodMenu> subFoodMenuList;
//
//    public FoodMenuDayAdapter(String subFoodMenuList) {
//        this.subFoodMenuList = subFoodMenuList;
//    }
//
//    @NonNull
//    @Override
//    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_foodmenu, viewGroup, false);
//        return new SubItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
//        FoodMenu foodMenu = foodMenuArrayList.get(position);
//        Log.i("이미지 출력이 안돼서 테스트 " , "이미지 출력 테스트" + foodMenu.getMealPhotoUrl());
//        holder.textTitle.setText(foodMenu.getMealContent() );
//        holder.textType.setText(foodMenu.getMealType());
//        Glide.with(context)
//                .load(foodMenu.getMealPhotoUrl())
//                .into(holder.photoContent);
//    }
//
//    @Override
//    public int getItemCount() {
//        return subItemList.size();
//    }
//
//    class SubItemViewHolder extends RecyclerView.ViewHolder {
//        TextView tvSubItemTitle;
//
//        SubItemViewHolder(View itemView) {
//            super(itemView);
//            tvSubItemTitle = itemView.findViewById(R.id.tv_sub_item_title);
//        }
//    }
//}
//
//
//
//
//
//
////
////
////public class FoodMenuDayAdapter {
////
////    private List<FoodMenu> subItemList;
////
////    FoodMenuDayAdapter(List<FoodMenu> subItemList) {
////        this.subItemList = subItemList;
////    }
////
////    @NonNull
////    @Override
////    public FoodMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
////        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_foodmenu, viewGroup, false);
////        return new FoodMenuViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull FoodMenuViewHolder subItemViewHolder, int i) {
////        FoodMenu subItem = subItemList.get(i);
////        subItemViewHolder.tvFoodMenuTitle.setText(subItem.getFoodMenuTitle());
////    }
////
////    @Override
////    public int getItemCount() {
////        return subItemList.size();
////    }
////
////    class FoodMenuViewHolder extends RecyclerView.ViewHolder {
////        TextView tvFoodMenuTitle;
////
////        FoodMenuViewHolder(View itemView) {
////            super(itemView);
////            tvFoodMenuTitle = itemView.findViewById(R.id.tv_sub_item_title);
////        }
////    }
////}
//
////
////    Context context;
////    ArrayList<FoodMenuDayList> foodMenuDayListArrayList;
////
////    public FoodMenuDayAdapter(Context context, ArrayList<FoodMenuDayList> foodMenuDayListArrayList) {
////        this.context = context;
////        this.foodMenuDayListArrayList = foodMenuDayListArrayList;
////    }
////
//////
//////    @NonNull
//////    @Override
//////    public FoodMenuDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//////        View view = LayoutInflater.from(parent.getContext())
//////                .inflate(R.layout.row_subrecyclerview, parent, false);
//////        return new FoodMenuDayAdapter.ViewHolder(view);
//////    }
//////
//////    @Override
//////    public void onBindViewHolder(@NonNull FoodMenuDayAdapter.ViewHolder holder, int position) {
//////        FoodMenuDayList foodMenuDayList = foodMenuDayListArrayList.get(position);
////////        holder.textDate.setText(foodMenuDayListArrayList.textDate);
//////
////////        holder.recyclerViewH.(foodMenu.getMealType());
//////    }
//////
//////    @Override
//////    public int getItemCount() {
//////        return foodMenuDayListArrayList.size();
//////    }
////
////    public class ViewHolder extends RecyclerView.ViewHolder {
////
////
////        TextView textDate;
////        RecyclerView recyclerViewH;
////
////        public ViewHolder(@NonNull View itemView) {
////            super(itemView);
////
////            textDate = itemView.findViewById(R.id.textDate);
////            recyclerViewH = itemView.findViewById(R.id.recyclerViewH);
////
////
////        }
////    }
////}
