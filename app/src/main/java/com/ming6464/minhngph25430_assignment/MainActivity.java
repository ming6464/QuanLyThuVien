package com.ming6464.minhngph25430_assignment;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ming6464.minhngph25430_assignment.DAO.DAO;
import com.ming6464.minhngph25430_assignment.Fragment.DoanhThuFragment;
import com.ming6464.minhngph25430_assignment.Fragment.QuanLyLoaiSachFragment;
import com.ming6464.minhngph25430_assignment.Fragment.QuanLyPhieuMuonFragment;
import com.ming6464.minhngph25430_assignment.Fragment.QuanLySachFragment;
import com.ming6464.minhngph25430_assignment.Fragment.QuanLyThanhVienFragment;
import com.ming6464.minhngph25430_assignment.Fragment.Top10Sach;
import com.ming6464.minhngph25430_assignment.RoomDB.Room_DB;
import com.ming6464.minhngph25430_assignment.SharePre.Share;
import com.ming6464.minhngph25430_assignment.databinding.ActivityMainBinding;
import com.ming6464.minhngph25430_assignment.model.ThuThu;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handlerNaviAndToolbar();
        addFragment();
    }

    private void addFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.actiMain_layout_linear, QuanLyPhieuMuonFragment.newInstance());
        transaction.commit();
    }

    private void handlerNaviAndToolbar() {
        setSupportActionBar(binding.actiMainTb);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,binding.actiMainDrawerLayout,binding.actiMainTb,
                R.string.open_drawer,R.string.close_drawer);
        binding.actiMainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.actiMainNv.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.nav_qlpm:
                    chuyenFragment(QuanLyPhieuMuonFragment.newInstance());
                    break;
                case R.id.nav_qlls:
                    chuyenFragment(QuanLyLoaiSachFragment.newInstance());
                    break;
                case R.id.nav_qls:
                    chuyenFragment(QuanLySachFragment.newInstance());
                    break;
                case R.id.nav_qltv:
                    chuyenFragment(QuanLyThanhVienFragment.newInstance());
                    break;
                case R.id.nav_top10Sach:
                    chuyenFragment(Top10Sach.newInstance());
                    break;
                case R.id.nav_doanhThu:
                    chuyenFragment(DoanhThuFragment.newInstance());
                    break;
                case R.id.nav_doiMatKhau:
                    handlerDoiMatKhau();
                    break;
                default:
                    startActivity(new Intent(this,DangNhapActivity.class));
                    finish();
            }
            binding.actiMainDrawerLayout.closeDrawer(binding.actiMainNv);
            return true;
        });
        TextView name_admin = binding.actiMainNv.getHeaderView(0).findViewById(R.id.layoutHeader_tv_name);
        name_admin.setText("Wellcome " + new Share(this).getName());
    }

    private void handlerDoiMatKhau() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_doi_mat_khau);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        EditText ed_mkMoi = dialog.findViewById(R.id.dialogDoiMatKhau_ed_matKhauMoi),ed_mkCu = dialog.findViewById(R.id.dialogDoiMatKhau_ed_matKhauCu);
        Button btn_dongy = dialog.findViewById(R.id.dialogDoiMatKhau_btn_dongY),btn_huy = dialog.findViewById(R.id.dialogDoiMatKhau_btn_huy);
        //
        btn_dongy.setOnClickListener(v -> {
            DAO dao = Room_DB.getInstance(MainActivity.this).getDAO();
            ThuThu obj = dao.getObjOfThuThu(new Share(MainActivity.this).getMaTT());
            if(obj.getMatKhau().equals(ed_mkCu.getText().toString())){
                obj.setMatKhau(ed_mkMoi.getText().toString());
                dao.updateOfThuThu(obj);
                Toast.makeText(this, "Đổi thành công", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }else{
                Toast.makeText(this, "Mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
            }
        });
        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    private void chuyenFragment(Fragment fragment){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.actiMain_layout_linear,fragment);
        transaction.commit();
    }

}