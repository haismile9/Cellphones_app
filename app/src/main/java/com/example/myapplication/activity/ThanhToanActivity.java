package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.retrofit.ApiBanHang;
import com.example.myapplication.retrofit.RetrofitClient;
import com.example.myapplication.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtsodt, txtemail;
    EditText eddiachi, edsodienthoai, edemail;
    AppCompatButton btndathang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kích hoạt EdgeToEdge cho Activity
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        initView();  // Khởi tạo các view
        countItem(); // Đếm tổng số lượng sản phẩm trong giỏ hàng
        initControl(); // Khởi tạo các sự kiện điều khiển
    }

    private void countItem() {
        totalItem = 0;
        // Tính tổng số lượng sản phẩm trong giỏ hàng
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }

    }

    private void initControl() {
        // Thiết lập Toolbar như ActionBar và thêm sự kiện quay lại
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        // Định dạng lại số tiền tổng
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));
        edemail.setText(decimalFormat.format(tongtien));
        edemail.setText(Utils.user_current.getEmail());
        edsodienthoai.setText(Utils.user_current.getMobile());

        // Xử lý sự kiện khi người dùng nhấn nút "Đặt hàng"
        btndathang.setOnClickListener(view -> {
            String str_diachi = eddiachi.getText().toString().trim();
            String str_email = edemail.getText().toString().trim();
            String str_sdt = edsodienthoai.getText().toString().trim();

            // Kiểm tra nếu địa chỉ, email hoặc số điện thoại trống, hiển thị thông báo lỗi
            if (TextUtils.isEmpty(str_diachi) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_sdt)) {
                Toast.makeText(ThanhToanActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu người dùng chưa đăng nhập, tạo đơn hàng với email và số điện thoại đã nhập
                int iduser = Utils.user_current != null ? Utils.user_current.getId() : 0;

                compositeDisposable.add(apiBanHang.createOrder(str_email, str_sdt, String.valueOf(tongtien), iduser, str_diachi, totalItem, new Gson().toJson(Utils.manggiohang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    Toast.makeText(getApplicationContext(), "Đơn hàng đã được tạo thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                },
                                throwable -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
                        ));
            }
        });

    }

    private void initView() {
        // Khởi tạo các đối tượng view và API
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toobar);
        txttongtien = findViewById(R.id.txttongtien);
        edsodienthoai = findViewById(R.id.edt_sodienthoai);
        edemail = findViewById(R.id.edt_email);
        eddiachi = findViewById(R.id.eddiachi);
        btndathang = findViewById(R.id.btndathang);

    }

    @Override
    protected void onDestroy() {
        // Dọn dẹp CompositeDisposable khi Activity bị hủy
        compositeDisposable.clear();
        super.onDestroy();
    }
}
