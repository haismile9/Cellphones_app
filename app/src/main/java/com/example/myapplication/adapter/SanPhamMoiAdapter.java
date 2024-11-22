package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.activity.ChiTietActivity;
import com.example.myapplication.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    private Context context;
    private List<SanPhamMoi> array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = array.get(position);

        // Cập nhật tên sản phẩm
        holder.txtten.setText(sanPhamMoi.getTensp());

        // Xử lý giá sản phẩm và định dạng
        if (sanPhamMoi.getGiasp() != null) {
            try {
                // Loại bỏ các ký tự không phải số và thay thế dấu phân cách thập phân nếu cần
                String giaStr = sanPhamMoi.getGiasp().replace(".", "").replace(",", ".");

                // Chuyển đổi giá từ chuỗi thành số
                double gia = Double.parseDouble(giaStr);

                // Định dạng giá với dấu phân cách hàng nghìn
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                String giaFormatted = decimalFormat.format(gia);
                holder.txtgia.setText("Giá: " + giaFormatted + " Đ");

            } catch (NumberFormatException e) {
                Log.e("SanPhamMoiAdapter", "Giá không hợp lệ: " + sanPhamMoi.getGiasp(), e);
                holder.txtgia.setText("Giá không hợp lệ");
            }
        } else {
            holder.txtgia.setText("Giá không có");
        }

        // Cập nhật hình ảnh sản phẩm
        if (sanPhamMoi.getHinhanh() != null) {
            Glide.with(context)
                    .load(sanPhamMoi.getHinhanh())
                    .placeholder(R.drawable.iconnew) // Hình ảnh thay thế khi đang tải
                    .error(R.drawable.iconnew) // Hình ảnh hiển thị khi tải lỗi
                    .into(holder.imghinhanh);
            holder.setItemClickListener(new ItemClickListener(){

                public void onClick(View view, int position, boolean isLongClick) {
                    if (!isLongClick){
                        Intent intent = new Intent(context, ChiTietActivity.class);
                        intent.putExtra("chitiet", sanPhamMoi);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            holder.imghinhanh.setImageResource(R.drawable.iconnew); // Hình ảnh mặc định nếu URL không có
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtten, txtgia;
        ImageView imghinhanh;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            imghinhanh = itemView.findViewById(R.id.item_img);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(),false);

        }
    }
}
