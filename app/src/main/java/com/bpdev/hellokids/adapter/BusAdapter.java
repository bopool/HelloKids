package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.MainActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.SchoolbusListActivity;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.model.Bus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder>{

    Context context;

    ArrayList<Bus> busArrayList;

    public BusAdapter(Context context, ArrayList<Bus> busArrayList) {
        this.context = context;
        this.busArrayList = busArrayList;
    }

    @NonNull
    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schoolbustest,parent,false);
        return new BusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.ViewHolder holder, int position) {

        Bus bus = busArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.txtBusName.setText(bus.getShuttleName());
        holder.txtBusNum.setText(bus.getShuttleNum());
        holder.txtBusTime.setText(bus.getShuttleTime());
        holder.txtDriver.setText(bus.getShuttleDriver());
        holder.txtDriverNum.setText(bus.getShuttleDriverNum());

    }

    @Override
    public int getItemCount() {
        return busArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtBusName;
        TextView txtBusNum;
        TextView txtBusTime;
        TextView txtDriver;
        TextView txtDriverNum;
        CardView cardView;
        ImageView imgBus;
        Button btnLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imgBus = itemView.findViewById(R.id.imgBus);
            btnLocation = itemView.findViewById(R.id.btnLocation);
            txtBusName = itemView.findViewById(R.id.txtBusName);
            txtBusNum = itemView.findViewById(R.id.txtBusNum);
            txtBusTime = itemView.findViewById(R.id.txtBusTime);
            txtDriver = itemView.findViewById(R.id.txtBusDriver);
            txtDriverNum = itemView.findViewById(R.id.txtBusDriverNum);

            btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SchoolbusListActivity.class);
                    context.startActivity(intent);
                }
            });

        }


    }
}
