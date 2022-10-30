package com.ming6464.minhngph25430_assignment.model;

import android.os.Build;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Entity
public class PhieuMuon {
    @PrimaryKey(autoGenerate = true)
    private int maPM;
    private int maTV,maSach;
    private int tienThue;
    private boolean trangThai;
    private String maTT;
    private Date ngayMuon;

    public PhieuMuon(String maTT, int maTV, int maSach, int tienThue, boolean trangThai, Date ngayMuon) {
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.trangThai = trangThai;
        this.maTT = maTT;
        this.ngayMuon = ngayMuon;
    }

    public PhieuMuon() {
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    @Override
    public String toString() {
        return
                "maPM=" + maPM +
                ", maTV=" + maTV +
                ", maSach=" + maSach +
                ", tienThue=" + tienThue +
                ", trangThai=" + trangThai +
                ", maTT='" + maTT + '\'' +
                ", ngayMuon='" + ngayMuon;
    }
}
