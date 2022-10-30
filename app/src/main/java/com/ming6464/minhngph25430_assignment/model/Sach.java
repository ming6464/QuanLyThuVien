package com.ming6464.minhngph25430_assignment.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sach {
    @PrimaryKey(autoGenerate = true)
    private int maSach;

    private int maLoai,giaThue,soTrang;
    private String tenSach,tieuDe;

    public Sach(int maLoai, int giaThue,int soTrang, String tenSach,String tieuDe) {
        this.maLoai = maLoai;
        this.giaThue = giaThue;
        this.tenSach = tenSach;
        this.soTrang = soTrang;
        this.tieuDe = tieuDe;
    }

    public Sach() {
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
