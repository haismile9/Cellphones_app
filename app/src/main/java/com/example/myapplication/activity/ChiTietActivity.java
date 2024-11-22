package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.GioHang;
import com.example.myapplication.model.SanPhamMoi;
import com.example.myapplication.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spiner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        // Khởi tạo các thành phần giao diện
        initView();
        // Nạp dữ liệu ban đầu
        initData();
        // Thiết lập các sự kiện điều khiển
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (sanPhamMoi == null || spiner.getSelectedItem() == null) {
            Log.e("ChiTietActivity", "Dữ liệu sản phẩm hoặc giá trị spinner không hợp lệ.");
            return;
        }

        if (Utils.manggiohang == null) {
            Utils.manggiohang = new ArrayList<>(); // Khởi tạo danh sách nếu chưa được khởi tạo
        }

        int soluong;
        try {
            soluong = Integer.parseInt(spiner.getSelectedItem().toString());
        } catch (NumberFormatException e) {
            Log.e("ChiTietActivity", "Số lượng không hợp lệ: " + spiner.getSelectedItem().toString(), e);
            return;
        }

        long gia;
        try {
            gia = Long.parseLong(sanPhamMoi.getGiasp().replaceAll("[^\\d]", "")); // Loại bỏ ký tự không phải số
        } catch (NumberFormatException e) {
            Log.e("ChiTietActivity", "Giá không hợp lệ: " + sanPhamMoi.getGiasp(), e);
            return;
        }

        boolean flag = false;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()) {
                Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                Utils.manggiohang.get(i).setGiasp(gia * Utils.manggiohang.get(i).getSoluong());
                flag = true;
                break;
            }
        }

        if (!flag) {
            GioHang gioHang = new GioHang();
            gioHang.setSoluong(soluong);
            gioHang.setGiasp(gia * soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }

        // Cập nhật số lượng sản phẩm trong giỏ hàng
        badge.setText(String.valueOf(Utils.manggiohang.size()));
    }



    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        if (sanPhamMoi != null) {
            tensp.setText(sanPhamMoi.getTensp());
            mota.setText(sanPhamMoi.getMota());
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            try {
                double price = Double.parseDouble(sanPhamMoi.getGiasp());
                giasp.setText("Giá: " + decimalFormat.format(price) + " Đ");
            } catch (NumberFormatException e) {
                giasp.setText("Giá: N/A");
            }
        } else {
            tensp.setText("Không có thông tin sản phẩm");
            mota.setText("");
            giasp.setText("Giá: N/A");
        }
        Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, so);
        spiner.setAdapter(adapterspin);
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        imghinhanh = findViewById(R.id.imgchitiet);
        spiner = findViewById(R.id.spiner);
        toolbar = findViewById(R.id.toolbar);
        badge = findViewById(R.id.menu_sl); // Đảm bảo ID khớp với XML
        FrameLayout frameLayout = findViewById(R.id.framegiohang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });


        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        // Thiết lập Toolbar làm ActionBar
        setSupportActionBar(toolbar);

        // Kiểm tra nếu ActionBar có giá trị null
        if (getSupportActionBar() == null) {
            Log.e("ChiTietActivity", "ActionBar is null");
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút quay lại
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Hiển thị nút home nếu cần
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity khi bấm nút quay lại
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }


            badge.setText(String.valueOf(totalItem));
        }
    }
}
