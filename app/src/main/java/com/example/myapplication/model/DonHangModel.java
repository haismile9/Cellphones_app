package com.example.myapplication.model;

import java.util.List;

public class DonHangModel {
    boolean success;
    String messeage;
    List<DonHang> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMesseage() {
        return messeage;
    }

    public void setMesseage(String messeage) {
        this.messeage = messeage;
    }

    public List<DonHang> getResult() {
        return result;
    }

    public void setResult(List<DonHang> result) {
        this.result = result;
    }
}
