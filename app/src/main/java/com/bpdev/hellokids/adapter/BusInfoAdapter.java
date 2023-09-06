package com.bpdev.hellokids.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.DailynoteViewActivity;
import com.bpdev.hellokids.MainActivity;
import com.bpdev.hellokids.NoticeListActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.ScheduleListActivity;
import com.bpdev.hellokids.ScheduleViewActivity;
import com.bpdev.hellokids.SchoolbusViewActivity;
import com.bpdev.hellokids.SettingSchoolbusEditActivity;
import com.bpdev.hellokids.SettingSchoolbusListActivity;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusInfo;
import com.bpdev.hellokids.model.BusRes;
import com.bpdev.hellokids.model.DailyNoteRow;
import com.bpdev.hellokids.model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BusInfoAdapter extends RecyclerView.Adapter<BusInfoAdapter.ViewHolder> {

    Context context;

    ArrayList<BusInfo> busInfoArrayList;

    int id;

    //String token;

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

        //token = ((SettingSchoolbusListActivity)SettingSchoolbusListActivity.mContext).token;
        String busTime = busInfo.getShuttleTime();
        id = busInfo.getId();

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

        TextView textBusInfo;

        Button btnEdit;
        Button btnDelete;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            textBusName = itemView.findViewById(R.id.textBusName);
            textGo = itemView.findViewById(R.id.textGo);
            textBusTime = itemView.findViewById(R.id.textBusTime);
            textBusInfo = itemView.findViewById(R.id.textBusInfo);
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

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });

            textBusInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    BusInfo busInfo = busInfoArrayList.get(index);
                    Intent intent = new Intent(context, SchoolbusViewActivity.class);
                    intent.putExtra("busInfo",busInfo);
                    context.startActivity(intent);
                }
            });

        }

//        public void showAlertDialog(){
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("정보 삭제");
//            builder.setMessage("정말 삭제하시겠습니까?");
//            builder.setNegativeButton("NO", null);
//            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    int index = getAdapterPosition();
//                    busInfoArrayList.remove(index);
//                    notifyDataSetChanged();
//                }
//            });
//            builder.show();
//        }

        void showDialog(){  // 블로그에 넣어서 언제든 복사 붙여넣기 하기
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("삭제");
            builder.setMessage("정말 삭제하시겠습니까?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    showProgress();

                    Retrofit retrofit = NetworkClient.getRetrofitClient(context);

                    BusApi api = retrofit.create(BusApi.class);

                    Call<BusRes> call = api.busInfoDelete(id);

                    call.enqueue(new Callback<BusRes>() {
                        @Override
                        public void onResponse(Call<BusRes> call, Response<BusRes> response) {
                            dismissProgress();

                            if(response.isSuccessful()){

                                Intent intent = new Intent(context, SettingSchoolbusListActivity.class);
                                context.startActivity(intent);

                                ((Activity)context).finish();


                            }else{
                                // 유저한테, 서버에 문제있다고 메시지 띄운다.

                            }
                        }

                        @Override
                        public void onFailure(Call<BusRes> call, Throwable t) {
                            dismissProgress();
                        }
                    });

                }
            });
            builder.setNegativeButton("NO",null);
            builder.setCancelable(true);
            builder.show();
        }

        Dialog dialog;

        void showProgress(){
            dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(new ProgressBar(context));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        void dismissProgress(){
            dialog.dismiss();
        }
    }
}
