package com.example.myapplication.model;

import java.util.List;

public class DonHang {
    int id;
    int iduser = 0;
    String diachi;
    String sodienthoai;
    String tongtien;
    String email;  // Thêm thuộc tính email
    List<Item> item;

    // Các phương thức getter và setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getEmail() {  // Thêm phương thức getter cho email
        return email;
    }

    public void setEmail(String email) {  // Thêm phương thức setter cho email
        this.email = email;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}

