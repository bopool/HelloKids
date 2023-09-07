package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.SchoolbusAddActivity;
import com.bpdev.hellokids.model.Teacher;

import java.util.ArrayList;

public class SchoolBusTeacherAdapter extends RecyclerView.Adapter<SchoolBusTeacherAdapter.ViewHolder> {

    Context context;

    ArrayList<Teacher> teacherArrayList;

    int id; // 선생님 id
    
    String name; //선생님 이름

    /* 리스너 인터페이스 구현부 */
    public interface CheckBoxClickListener {
        void onClickCheckBox(int flag, int pos);
    }

    /* 체크박스 리스너 */
    private CheckBoxClickListener mCheckBoxClickListener;

    public SchoolBusTeacherAdapter(Context context, ArrayList<Teacher> teacherArrayList) {
        this.context = context;
        this.teacherArrayList = teacherArrayList;
    }

    @NonNull
    @Override
    public SchoolBusTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schoolbusteacher,parent,false);
        return new SchoolBusTeacherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolBusTeacherAdapter.ViewHolder holder, int position) {
        Teacher teacher = teacherArrayList.get(position); // position는 해당 위치를 나타낸다

        holder.imgTeacherProfile.setImageResource(R.drawable.teacher);
        holder.textTeacherName.setText(teacher.getTeacherName());

        /* 체크박스 리스너 */
        holder.checkBoxAdd.setOnClickListener(v -> {
            if(holder.checkBoxAdd.isChecked()) {
                // 체크가 되어 있음
                //mCheckBoxClickListener.onClickCheckBox(1, position);
                id = teacher.getId(); // 선택한 선생님 id
                name = teacher.getTeacherName();
                Log.i("name",name);
                Intent intent = new Intent(context, SchoolbusAddActivity.class);
                intent.putExtra("teacherId",id);
                intent.putExtra("name",name);
                context.startActivity(intent);
            }
            else {
                // 체크가 되어있지 않음
                //mCheckBoxClickListener.onClickCheckBox(0, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTeacherProfile;
        TextView textTeacherName;
        CheckBox checkBoxAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTeacherProfile = itemView.findViewById(R.id.imgTeacherProfile);
            textTeacherName = itemView.findViewById(R.id.textTeacherName);
            checkBoxAdd = itemView.findViewById(R.id.checkBoxAdd);
        }
    }

    /* 리스너 메소드 */
    public void setOnCheckBoxClickListener(SchoolBusTeacherAdapter.CheckBoxClickListener clickCheckBoxListener) {
        this.mCheckBoxClickListener = clickCheckBoxListener;
    }
}
