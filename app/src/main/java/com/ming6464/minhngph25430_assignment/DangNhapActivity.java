package com.ming6464.minhngph25430_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ming6464.minhngph25430_assignment.DAO.DAO;
import com.ming6464.minhngph25430_assignment.RoomDB.Room_DB;
import com.ming6464.minhngph25430_assignment.SharePre.Share;
import com.ming6464.minhngph25430_assignment.databinding.ActivityDangNhapBinding;
import com.ming6464.minhngph25430_assignment.model.ThuThu;

import java.util.Objects;

public class DangNhapActivity extends AppCompatActivity {
    private ActivityDangNhapBinding binding;
    private DAO dao;
    private Share share;
    private ThuThu obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangNhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dao = Room_DB.getInstance(this).getDAO();
        dao.insertOfThuThu(new ThuThu("admin","minhngph25430","123"));
        handlerThongTinShare();
        addAction();
    }
    private void handlerThongTinShare() {
        share = new Share(this);
        obj = share.getAccount();
        if(obj != null){
            binding.actiDangNhapEdTaiKhoan.setText(obj.getMaTT());
            binding.actiDangNhapEdMatKhau.setText(obj.getMatKhau());
            binding.actiDangNhapChkLuuThongTin.setChecked(true);
        }
    }
    private void addAction() {
        binding.actiDangNhapBtnDangNhap.setOnClickListener(v -> {
            obj = dao.getObjOfThuThu(Objects.requireNonNull(binding.actiDangNhapEdTaiKhoan.getText()).toString());
            if(obj != null && obj.getMatKhau().equals(Objects.requireNonNull(binding.actiDangNhapEdMatKhau.getText()).toString())){
                boolean check = binding.actiDangNhapChkLuuThongTin.isChecked();
                share.putAccount(obj,check);
                Intent intent = new Intent(this,MainActivity.class);
                Toast.makeText(this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                return;
            }
            Toast.makeText(this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
        });
        binding.actiDangNhapBtnDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(this,DangKyActivity.class);
            startActivity(intent);
        });
    }
}