package com.bpdev.hellokids.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.MainActivity;
import com.bpdev.hellokids.NoticeAddActivity;
import com.bpdev.hellokids.R;
import com.bpdev.hellokids.ScheduleChildListActivity;
import com.bpdev.hellokids.SettingChildProfileActivity;
import com.bpdev.hellokids.model.Child;
import com.bpdev.hellokids.model.ChildInfo;
import com.bpdev.hellokids.model.Teacher;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChildInfoAdapter extends RecyclerView.Adapter<ChildInfoAdapter.ViewHolder> {

    Context context;

    ArrayList<ChildInfo> childInfoArrayList;

    int id;

    public ChildInfoAdapter(Context context, ArrayList<ChildInfo> childInfoArrayList) {
        this.context = context;
        this.childInfoArrayList = childInfoArrayList;
    }

    @NonNull
    @Override
    public ChildInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_setting_childinfo, parent, false);
        return new ChildInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildInfoAdapter.ViewHolder holder, int position) {
        ChildInfo childInfo = childInfoArrayList.get(position); // position는 해당 위치를 나타낸다

        id = childInfo.getId();

        holder.textChildName.setText(childInfo.getChildName());
        holder.textBirthDate.setText(childInfo.getBirth());
        Glide.with(context)
                .load(childInfo.getProfileUrl())
                .into(holder.imgKidsProfile);
    }

    @Override
    public int getItemCount() {
        return childInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textChildName;
        TextView textBirthDate;
        ImageView imgKidsProfile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textChildName = itemView.findViewById(R.id.textChildName);
            textBirthDate = itemView.findViewById(R.id.textBirthDate);
            imgKidsProfile = itemView.findViewById(R.id.imgKidsProfile);

            imgKidsProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SettingChildProfileActivity.class);
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }
            });
        }


    }
}
