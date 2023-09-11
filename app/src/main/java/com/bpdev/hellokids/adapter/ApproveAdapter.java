package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.ScheduleEditActivity;
import com.bpdev.hellokids.ScheduleListActivity;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.api.ScheduleApi;
import com.bpdev.hellokids.api.SettingApi;
import com.bpdev.hellokids.config.Config;
import com.bpdev.hellokids.model.ApproveRes;
import com.bpdev.hellokids.model.AttendanceRes;
import com.bpdev.hellokids.model.Parents;
import com.bpdev.hellokids.model.Result;
import com.bpdev.hellokids.model.Schedule;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApproveAdapter extends RecyclerView.Adapter<ApproveAdapter.ViewHolder>{

    Context context;

    ArrayList<Parents> parentsArrayList;

    int parentsId;

    public ApproveAdapter(Context context, ArrayList<Parents> parentsArrayList) {
        this.context = context;
        this.parentsArrayList = parentsArrayList;
    }

    @NonNull
    @Override
    public ApproveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_setting_approve,parent,false);
        return new ApproveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveAdapter.ViewHolder holder, int position) {
        Parents parents = parentsArrayList.get(position); // position는 해당 위치를 나타낸다
        parentsId = parents.getId();

        holder.textChildName.setText(parents.getChildNameP());
    }

    @Override
    public int getItemCount() {
        return parentsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textChildName;
        Button btnApprove;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textChildName = itemView.findViewById(R.id.textChildName);
            btnApprove = itemView.findViewById(R.id.btnApprove);

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Retrofit retrofit = NetworkClient.getRetrofitClient(context);

                    SettingApi api = retrofit.create(SettingApi.class);

                    Call<ApproveRes> call = api.approve(parentsId);

                    call.enqueue(new Callback<ApproveRes>() {
                        @Override
                        public void onResponse(Call<ApproveRes> call, Response<ApproveRes> response) {


                            if(response.isSuccessful()){

                                Toast.makeText(view.getContext(), "승인되었습니다", Toast.LENGTH_SHORT).show();

                            }else{

                            }
                        }

                        @Override
                        public void onFailure(Call<ApproveRes> call, Throwable t) {

                        }
                    });

                }
            });
        }
    }
}
