package com.ming6464.minhngph25430_assignment.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ming6464.minhngph25430_assignment.Adapter.ThanhVienAdapter;
import com.ming6464.minhngph25430_assignment.DAO.DAO;
import com.ming6464.minhngph25430_assignment.R;
import com.ming6464.minhngph25430_assignment.RoomDB.Room_DB;
import com.ming6464.minhngph25430_assignment.databinding.FragmentQuanLyThanhVienBinding;
import com.ming6464.minhngph25430_assignment.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class QuanLyThanhVienFragment extends Fragment implements ThanhVienAdapter.OnAction {
    private ThanhVienAdapter adapter;
    private List<ThanhVien> list;
    private DAO dao;
    private FragmentQuanLyThanhVienBinding binding;
    public static QuanLyThanhVienFragment newInstance() {
        QuanLyThanhVienFragment fragment = new QuanLyThanhVienFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuanLyThanhVienBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = Room_DB.getInstance(requireContext()).getDAO();
    }

    @Override
    public void onResume() {
        super.onResume();
        list = new ArrayList<>();
        list = dao.getAllOfThanhVien();
        handlerRycler();
        handlerAction();
    }

    private void handlerAction() {
        binding.fragQuanLyThanhVienBtnThemThanhVien.setOnClickListener(v -> {
            String name = binding.fragQuanLyThanhVienEdHoTen.getText().toString();
            String namSinh = binding.fragQuanLyThanhVienEdNamSinh.getText().toString();
            if(name.isEmpty() || namSinh.isEmpty()){
                Toast.makeText(requireContext(), "Dữ liệu bị trống", Toast.LENGTH_SHORT).show();
                return;
            }
            ThanhVien obj = new ThanhVien(name,namSinh);
            dao.insertOfThanhVien(obj);
            list.add(dao.getObjNewOfThanhVien());
            adapter.notifyItemInserted(list.size() - 1);
            Toast.makeText(requireContext(), "Thêm thành công !", Toast.LENGTH_SHORT).show();
            binding.fragQuanLyThanhVienEdNamSinh.setText("");
            binding.fragQuanLyThanhVienEdHoTen.setText("");
        });

    }

    private void handlerRycler() {
        adapter = new ThanhVienAdapter(this);
        binding.fragQuanLyThanhVienRc.setAdapter(adapter);
        binding.fragQuanLyThanhVienRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.setData(list);
    }

    @Override
    public void actionSua(int position) {
        ThanhVien obj = list.get(position);
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_sua_thanh_vien);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        EditText hoTen = dialog.findViewById(R.id.dialogSuaThanhVien_ed_hoTen),namSinh = dialog.findViewById(R.id.dialogSuaThanhVien_ed_namSinh);
        Button dongY = dialog.findViewById(R.id.dialogSuaThanhVien_btn_dongY),huy = dialog.findViewById(R.id.dialogSuaThanhVien_btn_huy);
        //
        hoTen.setText(obj.getHoTen());
        namSinh.setText(obj.getNamSinh());
        //
        dongY.setOnClickListener(v -> {
            if(hoTen.getText().toString().isEmpty()){
                Toast.makeText(requireContext(),"Dữ liệu bị trống", Toast.LENGTH_SHORT).show();
                return;
            }
            obj.setNamSinh(namSinh.getText().toString());
            obj.setHoTen(hoTen.getText().toString());
            dao.updateOfThanhVien(obj);
            list.set(position,obj);
            adapter.notifyItemChanged(position);
            Toast.makeText(requireContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });
        huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }

    @Override
    public void actionXoa(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Xác nhận xoá");
        builder.setNegativeButton("Xoá", (dialogInterface, i) -> {
            dao.deleteOfThanhVien(list.get(position));
            list.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(requireContext(), "Xoá thành công !", Toast.LENGTH_SHORT).show();
        });
        builder.setPositiveButton("Huỷ", (dialogInterface, i) -> {
        });
        builder.show();
    }
}