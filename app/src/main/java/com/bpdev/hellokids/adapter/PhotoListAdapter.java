package com.bpdev.hellokids.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.Viewholder>{


    Context context;




    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }





    public class Viewholder extends  RecyclerView.ViewHolder {

        TextView textDate;
        TextView textTitle;
        TextView textContent;
        Button btnView;
        ImageView imgPhoto1;
        ImageView imgPhoto2;
        ImageView imgPhoto3;
        ImageView imgPhoto4;
        CardView cardView;



        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //
            textDate = itemView.findViewById(R.id.textDate);
            textTitle = itemView.findViewById(R.id.textTitle);
            textContent = itemView.findViewById(R.id.textContent);
            btnView = itemView.findViewById(R.id.btnView);
            imgPhoto1 = itemView.findViewById(R.id.imgPhoto1);
            imgPhoto2 = itemView.findViewById(R.id.imgPhoto2);
            imgPhoto3 = itemView.findViewById(R.id.imgPhoto3);
            imgPhoto4 = itemView.findViewById(R.id.imgPhoto4);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }

}
