package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.DailyNoteRes;
import com.bpdev.hellokids.model.ScheduleRes;

import java.util.ArrayList;

public class DailyNoteAdapter extends RecyclerView.Adapter<DailyNoteAdapter.ViewHolder> {

    Context context;

    ArrayList<DailyNoteRes> dailyNoteArrayList;

    @NonNull
    @Override
    public DailyNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dailynote_list,parent,false);
        return new DailyNoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyNoteAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dailyNoteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textDate;
        TextView textDailyTitle;
        TextView textDailyContents;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textDate = itemView.findViewById(R.id.textDate);
            textDailyTitle = itemView.findViewById(R.id.textDailyTitle);
            textDailyContents = itemView.findViewById(R.id.textDailyContents);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
