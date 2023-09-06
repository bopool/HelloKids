package com.bpdev.hellokids.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bpdev.hellokids.R;
import com.bpdev.hellokids.model.PhotoAlbum;
import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class PhotoAddAdapter extends RecyclerView.Adapter<PhotoAddAdapter.ViewHolder> {

    public ArrayList<Uri> mData;
    public Context context;




 //-- 사진 여러장 리사이클러뷰에 표시할 때 사용
    public PhotoAddAdapter(ArrayList<Uri> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }






    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // LayoutInflater - XML에 정의된 Resource(자원) 들을 View의 형태로 반환.
    @Override
    public PhotoAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;    // context에서 LayoutInflater 객체를 얻는다.
        View view = inflater.inflate(R.layout.row_photoalbum_add, parent, false) ;	// 리사이클러뷰에 들어갈 아이템뷰의 레이아웃을 inflate.
        PhotoAddAdapter.ViewHolder vh = new PhotoAddAdapter.ViewHolder(view) ;
        return vh ;
    }



    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PhotoAddAdapter.ViewHolder holder, int position) {
        Uri image_uri = mData.get(position);
        Log.i("PhotoAddAdapter Uri", ""+image_uri);
        Glide.with(context)
                .load(image_uri)
                .into(holder.photoContent);
    }



    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        Log.i("PhotoAddAdapter size", ""+mData.size());
        return mData.size();
    }



    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoContent;
//        ImageView imgPhotoAdd;
//        ImageView imgPhotoAdd1;
//        ImageView imgPhotoAdd2;
//        ImageView imgPhotoAdd3;
//        ImageView imgPhotoAdd4;
//        ImageView imgPhotoAdd5;
//        ImageView imgPhotoAdd6;
//        ImageView imgPhotoAdd7;
//        ImageView imgPhotoAdd8;
//        ImageView imgPhotoAdd9;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조.
            photoContent = itemView.findViewById(R.id.photoContent);
//            imgPhotoAdd = itemView.findViewById(R.id.imgPhotoAdd);
//            imgPhotoAdd1 = itemView.findViewById(R.id.imgPhotoAdd1);
//            imgPhotoAdd2 = itemView.findViewById(R.id.imgPhotoAdd2);
//            imgPhotoAdd3 = itemView.findViewById(R.id.imgPhotoAdd3);
//            imgPhotoAdd4 = itemView.findViewById(R.id.imgPhotoAdd4);
//            imgPhotoAdd5 = itemView.findViewById(R.id.imgPhotoAdd5);
//            imgPhotoAdd6 = itemView.findViewById(R.id.imgPhotoAdd6);
//            imgPhotoAdd7 = itemView.findViewById(R.id.imgPhotoAdd7);
//            imgPhotoAdd8 = itemView.findViewById(R.id.imgPhotoAdd8);
//            imgPhotoAdd9 = itemView.findViewById(R.id.imgPhotoAdd9);

        }
    }
}
