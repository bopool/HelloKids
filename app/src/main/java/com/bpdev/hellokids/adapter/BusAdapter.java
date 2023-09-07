package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bpdev.hellokids.R;
import com.bpdev.hellokids.RegisterSelectActivity;
import com.bpdev.hellokids.SchoolbusInfoActivity;
import com.bpdev.hellokids.SchoolbusLocationActivity;
import com.bpdev.hellokids.model.BusDailyRecord;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder>{

    Context context;

    ArrayList<BusDailyRecord> busArrayList;

    /* 리스너 인터페이스 구현부 */
    public interface CheckBoxClickListener {
        void onClickCheckBox(int flag, int pos);
    }

    /* 체크박스 리스너 */
    private CheckBoxClickListener mCheckBoxClickListener;

    int id = 0;

    int busId = 0; //버스 정보 id

    int teacherId = 0; // 인솔교사 id


    public BusAdapter(Context context, ArrayList<BusDailyRecord> busArrayList) {
        this.context = context;
        this.busArrayList = busArrayList;
    }

    @NonNull
    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schoolbus,parent,false);
        return new BusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.ViewHolder holder, int position) {

        BusDailyRecord bus = busArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.textBusName.setText(bus.getShuttleName());


        holder.textDriveStart.setText(bus.getShuttleStart().substring(11,13)+"시"+bus.getShuttleStart().substring(14,16)+"분");
        holder.textDriveEnd.setText(bus.getShuttleStop().substring(11,13)+"시"+bus.getShuttleStop().substring(14,16)+"분");

        holder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SchoolbusLocationActivity.class);
                context.startActivity(intent);
            }
        });

        /* 체크박스 리스너 */
        holder.checkBoxBus.setOnClickListener(v -> {
            if(holder.checkBoxBus.isChecked()) {
                // 체크가 되어 있음
                //mCheckBoxClickListener.onClickCheckBox(1, position);
                id = bus.getId(); // 선택한 운행 기록 id
                busId = bus.getSchoolbusId();
                teacherId = bus.getShuttleTeacherId();

                Log.i("id",id+"");
                Log.i("busId",busId+"");
                Log.i("teacherId",teacherId+"");

                Intent intent = new Intent(context,SchoolbusInfoActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("busId",busId);
                intent.putExtra("teacherId",teacherId);
                context.startActivity(intent);
            }
            else {
                // 체크가 되어있지 않음
                //mCheckBoxClickListener.onClickCheckBox(0, position);
            }
        });

        holder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = bus.getId(); // 선택한 운행 기록 id
                String strId = Integer.toString(id);
                Intent intent = new Intent(context,SchoolbusLocationActivity.class);
                intent.putExtra("strId",strId);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return busArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textBusName;
        TextView textBusNum;
        TextView textDriveStart;
        TextView textDriveOk;
        TextView textDriveEnd;
        CardView cardView;
        ImageView imgBus;
        Button btnLocation;
        CheckBox checkBoxBus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);

            textBusName = itemView.findViewById(R.id.txtBusName);
            textBusNum = itemView.findViewById(R.id.txtBusNum);
            textDriveStart = itemView.findViewById(R.id.txtDriveStart);
            textDriveEnd = itemView.findViewById(R.id.txtDriveEnd);

            imgBus = itemView.findViewById(R.id.imgBus);
            btnLocation = itemView.findViewById(R.id.btnLocation);
            checkBoxBus = itemView.findViewById(R.id.checkBoxBus);


        }
    }
    /* 리스너 메소드 */
    public void setOnCheckBoxClickListener(CheckBoxClickListener clickCheckBoxListener) {
        this.mCheckBoxClickListener = clickCheckBoxListener;
    }

}
