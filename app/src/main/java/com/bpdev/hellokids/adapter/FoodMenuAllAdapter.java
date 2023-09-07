//package com.bpdev.hellokids.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bpdev.hellokids.R;
//import com.bpdev.hellokids.model.FoodMenu;
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//import java.util.List;
//
//// 상위 어답터
//public class FoodMenuAllAdapter extends RecyclerView.Adapter<FoodMenuAllAdapter.FoodMenuViewHolder> {
//
//    public FoodMenuAllAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList, RecyclerView.RecycledViewPool viewPool) {
//        this.context = context;
//        this.foodMenuArrayList = foodMenuArrayList;
//        this.viewPool = viewPool;
//    }
//
//    Context context;
//    ArrayList<FoodMenu> foodMenuArrayList;
//    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//
//
//    @NonNull
//    @Override
//    public FoodMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_foodmenu, viewGroup, false);
//        return new FoodMenuViewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FoodMenuViewHolder foodMenuViewHolder, int i) {
//        FoodMenu foodMenu = foodMenuArrayList.get(i);
//        foodMenuViewHolder.textDate.setText(foodMenu.getMealDate());
//
//        // 자식 레이아웃 매니저 설정
//        LinearLayoutManager layoutManager = new LinearLayoutManager(
//                foodMenuViewHolder.recyclerViewH.getContext(),
//                LinearLayoutManager.HORIZONTAL,
//                false
//        );
//        layoutManager.setInitialPrefetchFoodMenuCount(3);
//
//        // 자식 어답터 설정
//        FoodMenuDayAdapter foodMenuDayAdapter = new FoodMenuDayAdapter(foodMenu.getMealDate());
//
//        foodMenuViewHolder.recyclerViewH.setLayoutManager(layoutManager);
//        foodMenuViewHolder.recyclerViewH.setAdapter(foodMenuDayAdapter);
//        foodMenuViewHolder.recyclerViewH.setRecycledViewPool(viewPool);
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
//    class FoodMenuViewHolder extends RecyclerView.ViewHolder {
//        private TextView textDate;
//        private RecyclerView recyclerViewH;
//
//        FoodMenuViewHolder(View foodMenuView) {
//            super(foodMenuView);
//            // 부모 타이틀
//            textDate = foodMenuView.findViewById(R.id.textDate);
//            // 자식아이템 영역
//            recyclerViewH = foodMenuView.findViewById(R.id.recyclerViewH);
//        }
//    }
//}
//
////
////    Context context;
////    ArrayList<FoodMenu> foodMenuArrayList;
////
////    public FoodMenuAllAdapter(Context context, ArrayList<FoodMenu> foodMenuArrayList) {
////        this.context = context;
////        this.foodMenuArrayList = foodMenuArrayList;
////    }
////
////
////    @NonNull
////    @Override
////    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext())
////                .inflate(R.layout.row_foodmenu, parent, false);
////        return new FoodMenuAllAdapter.ViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
////        FoodMenu foodMenu = foodMenuArrayList.get(position);
////
////        Glide.with(context)
////                .load(foodMenu.getMealPhotoUrl())
////                .into(holder.photoContent);
////        holder.textTitle.setText(foodMenu.getMealContent() );
////        holder.textType.setText(foodMenu.getMealType());
////    }
////
////    @Override
////    public int getFoodMenuCount() {
////        return foodMenuArrayList.size();
////    }
////
////    public class ViewHolder extends RecyclerView.ViewHolder {
////
////        ImageView photoContent;
////        TextView textTitle;
////        TextView textType;
////
////
////        public ViewHolder(@NonNull View foodMenuView) {
////            super(foodMenuView);
////
////            photoContent = foodMenuView.findViewById(R.id.photoContent);
////            textTitle = foodMenuView.findViewById(R.id.textTitle);
////            textType = foodMenuView.findViewById(R.id.textType);
////
////
////
////        }
////    }
////}
