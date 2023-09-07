package com.bpdev.hellokids.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;

import java.util.List;

// 상위 어답터
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_subrecyclerview, viewGroup, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Item item = itemList.get(i);
        itemViewHolder.textDate.setText(item.getItemTitle());

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.subRecyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

        // 자식 어답터 설정
        SubItemAdapter subItemAdapter = new SubItemAdapter(item.getSubItemList());

        itemViewHolder.subRecyclerView.setLayoutManager(layoutManager);
        itemViewHolder.subRecyclerView.setAdapter(subItemAdapter);
        itemViewHolder.subRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textDate;
        public RecyclerView subRecyclerView;

        ItemViewHolder(View itemView) {
            super(itemView);
            // 부모 타이틀
            textDate = itemView.findViewById(R.id.textDate);
            // 자식아이템 영역
            subRecyclerView = itemView.findViewById(R.id.subRecyclerView);
        }
    }
}