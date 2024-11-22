package com.example.myapplication.activity;

import static com.example.myapplication.utils.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.R;
import com.example.myapplication.retrofit.ApiBanHang;
import com.example.myapplication.retrofit.RetrofitClient;
import com.example.myapplication.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangki, txtresetpass;
    EditText email, pass;
    AppCompatButton btndangnhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);

        // Khởi tạo Paper
        Paper.init(getApplicationContext());

        initView();
        initControll();

        // Kiểm tra nếu đã có email và pass trong PaperDB
        if (Paper.book().read("email") != null && Paper.book().read("pass") != null) {
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
            if (Paper.book().read("isLogin") != null) {
                boolean flag = Paper.book().read("isLogin");
                if (flag) {
                    // Nếu đã đăng nhập, chuyển hướng đến màn hình chính với delay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 1000);
                }
            }
        }
    }


    private void initControll() {
        txtdangki.setOnClickListener(view -> {
            Intent intent = new Intent(DangNhapActivity.this, DangKiActivity.class);
            startActivity(intent);
        });

        txtresetpass.setOnClickListener(view -> {
            Intent intent = new Intent(DangNhapActivity.this, ResetPassActivity.class);
            startActivity(intent);
        });

        btndangnhap.setOnClickListener(view -> {
            String str_email = email.getText().toString().trim();
            String str_pass = pass.getText().toString().trim();

            if (TextUtils.isEmpty(str_email)) {
                Toast.makeText(DangNhapActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(str_pass)) {
                Toast.makeText(DangNhapActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                // Lưu thông tin đăng nhập vào PaperDB
                Paper.book().write("email", str_email);
                Paper.book().write("pass", str_pass);

                // Gọi hàm đăng nhập
                dangNhap(str_email, str_pass);
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(BASE_URL).create(ApiBanHang.class);
        txtdangki = findViewById(R.id.txtdangki);
        txtresetpass = findViewById(R.id.txtresetpass);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btndangnhap = findViewById(R.id.btndangnhap);
    }

    private void dangNhap(String email, String pass) {
        compositeDisposable.add(apiBanHang.dangNhap(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                isLogin = true;
                                Paper.book().write("isLogin", isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                // Lưu thông tin người dùng vào PaperDB
                                Paper.book().write("user", userModel.getResult().get(0));
                                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
                ));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current != null) {
            if (Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null) {
                email.setText(Utils.user_current.getEmail());
                pass.setText(Utils.user_current.getPass());
            }
        } else {
            // Xử lý khi user_current là null
            email.setText(""); // Đảm bảo không có thông tin cũ
            pass.setText("");  // Đảm bảo không có thông tin cũ
            Toast.makeText(DangNhapActivity.this, "User chưa được đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
