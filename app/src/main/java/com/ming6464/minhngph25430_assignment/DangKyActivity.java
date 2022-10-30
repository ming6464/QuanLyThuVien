package com.ming6464.minhngph25430_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.ming6464.minhngph25430_assignment.DAO.DAO;
import com.ming6464.minhngph25430_assignment.RoomDB.Room_DB;
import com.ming6464.minhngph25430_assignment.databinding.ActivityDangKyBinding;
import com.ming6464.minhngph25430_assignment.model.ThuThu;

public class DangKyActivity extends AppCompatActivity {
    private ActivityDangKyBinding binding;
    private DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangKyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dao = Room_DB.getInstance(this).getDAO();
        addAction();
    }

    private void addAction() {
        binding.actiDangKyBtnDangKy.setOnClickListener(v -> {
            String tenThuThu = binding.actiDangKyEdTenThuThu.getText().toString(),
                    taiKhoan = binding.actiDangKyEdTaiKhoan.getText().toString(),
                    matKhau = binding.actiDangKyEdMatKhau.getText().toString(),
                    matKhau2 = binding.actiDangKyEdMatKhau2.getText().toString();

            if(tenThuThu.isEmpty() || taiKhoan.isEmpty() || matKhau.isEmpty() || matKhau2.isEmpty()){
                Toast.makeText(this, "Thông tin bị thiếu !", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!matKhau.equals(matKhau2)){
                Toast.makeText(this, "Mật khẩu không giống nhau !", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!dao.insertOfThuThu(new ThuThu(taiKhoan,tenThuThu,matKhau))){
                Toast.makeText(this, "Tài khoản đã tồn tại !", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
        });
        binding.actiDangKyBtnHuy.setOnClickListener(v -> {
            finish();
        });
    }
}