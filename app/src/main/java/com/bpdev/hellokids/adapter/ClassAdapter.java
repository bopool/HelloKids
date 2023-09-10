package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.MyClass;
import com.bpdev.hellokids.model.NurseryClass;
import com.bpdev.hellokids.model.ScheduleRes;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    Context context;

    ArrayList<NurseryClass> classArrayList;

    public ClassAdapter(Context context, ArrayList<NurseryClass> classArrayList) {
        this.context = context;
        this.classArrayList = classArrayList;
    }

    @NonNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_setting_classinfo,parent,false);
        return new ClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder holder, int position) {
        NurseryClass nurseryClass = classArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.textClass.setText(nurseryClass.getClassName());

    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textClass;
        Button btnEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textClass = itemView.findViewById(R.id.textClass);
            btnEdit = itemView.findViewById(R.id.btnEdit);

        }
    }
}
