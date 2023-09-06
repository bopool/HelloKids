package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.DailynoteListActivity;
import com.bpdev.hellokids.DailynoteViewActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.DailyNoteRow;

import java.util.ArrayList;

public class DailyNoteParentsAdapter  extends RecyclerView.Adapter<DailyNoteParentsAdapter.ViewHolder>{

    Context context;

    ArrayList<DailyNoteRow> dailyNoteArrayList;

    public DailyNoteParentsAdapter(Context context, ArrayList<DailyNoteRow> dailyNoteArrayList) {
        this.context = context;
        this.dailyNoteArrayList = dailyNoteArrayList;
    }

    @NonNull
    @Override
    public DailyNoteParentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dailynote_list,parent,false);
        return new DailyNoteParentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyNoteParentsAdapter.ViewHolder holder, int position) {
        DailyNoteRow dailyNoteRow = dailyNoteArrayList.get(position); // position는 해당 위치를 나타낸다
        String date = dailyNoteRow.getCreatedAt().substring(0,10);


        holder.textDate.setText(dailyNoteRow.getTitle());
        holder.textDailyTitle.setText(dailyNoteRow.getContents());
        holder.textDailyContents.setText(date);
    }

    @Override
    public int getItemCount() {
        return dailyNoteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
                    int index = getAdapterPosition();
                    DailyNoteRow dailyNoteRow = dailyNoteArrayList.get(index);
                    Intent intent = new Intent(context, DailynoteViewActivity.class);
                    intent.putExtra("dailyNoteRow",dailyNoteRow);
                    context.startActivity(intent);

                }
            });
        }
    }
}
