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

import com.bpdev.hellokids.NoticeListActivity;
import com.bpdev.hellokids.NoticeViewActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.Notice;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    Context context;
    ArrayList<Notice> noticeArrayList;

    public NoticeAdapter(Context context, ArrayList<Notice> noticeArrayList) {
        this.context = context;
        this.noticeArrayList = noticeArrayList;
    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notice_list, parent, false);
        return new NoticeAdapter.ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notice notice = noticeArrayList.get(position);
        Log.i("이미지 출력이 안돼서 테스트 " , "이미지 출력 테스트" + position);
        holder.txtDate.setText(notice.getNoticeDate() );
        holder.noticeListTitle.setText(notice.getNoticeTitle());
        holder.noticeListContents.setText(notice.getNoticeContents());
        Glide.with(context)
                .load(notice.getNoticePhotoUrl())
                .into(holder.noticeListPhoto);
    }


    @Override
    public int getItemCount() {
        return noticeArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate;
        TextView noticeListTitle;
        TextView noticeListContents;
        ImageView noticeListPhoto;
        CardView noticeListCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            noticeListTitle = itemView.findViewById(R.id.noticeListTitle);
            noticeListContents = itemView.findViewById(R.id.noticeListContents);
            noticeListPhoto = itemView.findViewById(R.id.noticeListPhoto);
            noticeListCardView = itemView.findViewById(R.id.noticeListCardView);

            noticeListCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int index = getAdapterPosition();
                    Log.i("어댑터 index" , ""+index+1);
                    Notice notice = noticeArrayList.get(index);

                    Intent intent = new Intent(context, NoticeViewActivity.class);
                    intent.putExtra("notice", notice);
                    intent.putExtra("index", index);
                    ((NoticeListActivity)context).launcher.launch(intent);


                }
            });

        }
    }
}
