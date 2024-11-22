package com.example.myapplication.model;

public class LoaiSp {
     String tensanpham;
     String hinhanh;



    public LoaiSp(String tensanpham, String hinhanh) {
        this.tensanpham = tensanpham;
        this.hinhanh = hinhanh;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    // Thêm setter nếu cần thiết
    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
