package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.DailynoteViewActivity;
import com.bpdev.hellokids.MainActivity;
import com.bpdev.hellokids.NoticeListActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.SettingSchoolbusEditActivity;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.DailyNoteRow;

import java.util.ArrayList;

public class BusInfoAdapter extends RecyclerView.Adapter<BusInfoAdapter.ViewHolder> {

    Context context;

    ArrayList<BusInfo> busInfoArrayList;

    public BusInfoAdapter(Context context, ArrayList<BusInfo> busInfoArrayList) {
        this.context = context;
        this.busInfoArrayList = busInfoArrayList;
    }

    @NonNull
    @Override
    public BusInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_setting_schoolbus_list,parent,false);
        return new BusInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusInfoAdapter.ViewHolder holder, int position) {

        BusInfo busInfo = busInfoArrayList.get(position); // position는 해당 위치를 나타낸다

        String busTime = busInfo.getShuttleTime();

        int hour = Integer.parseInt(busTime.substring(11,12));
        if(hour == 0) {
            holder.textGo.setText("등원");
        }else{
            holder.textGo.setText("하원");
        }
        holder.textBusName.setText(busInfo.getShuttleName());
        
        holder.textBusTime.setText(busInfo.getShuttleTime().substring(11,13)+"시"+busInfo.getShuttleTime().substring(14,16)+"분");

    }

    @Override
    public int getItemCount() {
        return busInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textBusName;
        TextView textGo;
        TextView textBusTime;

        Button btnEdit;
        Button btnDelete;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            textBusName = itemView.findViewById(R.id.textBusName);
            textGo = itemView.findViewById(R.id.textGo);
            textBusTime = itemView.findViewById(R.id.textBusTime);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    BusInfo busInfo = busInfoArrayList.get(index);
                    Intent intent = new Intent(context, SettingSchoolbusEditActivity.class);
                    intent.putExtra("busInfo",busInfo);
                    context.startActivity(intent);
                }
            });

        }
    }
}
