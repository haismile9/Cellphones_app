package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.LoaiSp;

import java.util.List;

import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

public class LoaiSpAdapter extends BaseAdapter {
    List<LoaiSp> arrayList;
    Context context;

    // Constructor
    public LoaiSpAdapter(Context context, List<LoaiSp> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        TextView texttensp;   // Sửa thành TextView cho tên sản phẩm
        ImageView imghinhanh; // Sửa thành ImageView cho hình ảnh
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, viewGroup, false);


            // Sửa thành đúng kiểu cho từng view
            viewHolder.texttensp = view.findViewById(R.id.item_tensp);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Set dữ liệu cho view
        LoaiSp loaiSp = arrayList.get(i);
        viewHolder.texttensp.setText(loaiSp.getTensanpham());
        Glide.with(context).load(loaiSp.getHinhanh()).into(viewHolder.imghinhanh);

        return view;
    }
}
