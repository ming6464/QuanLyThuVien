package com.ming6464.minhngph25430_assignment.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ming6464.minhngph25430_assignment.Adapter.SachAdapter;
import com.ming6464.minhngph25430_assignment.DAO.DAO;
import com.ming6464.minhngph25430_assignment.R;
import com.ming6464.minhngph25430_assignment.RoomDB.Room_DB;
import com.ming6464.minhngph25430_assignment.databinding.FragmentQuanLySachBinding;
import com.ming6464.minhngph25430_assignment.model.Sach;
import java.util.List;

public class QuanLySachFragment extends Fragment implements SachAdapter.OnEventSachAdapter {
    private FragmentQuanLySachBinding binding;
    private SachAdapter adapter;
    boolean check;
    private ArrayAdapter arrayAdapter;
    private List<String> listMaLoaiSach;
    private DAO dao;
    private List<Sach> list;
    public static QuanLySachFragment newInstance() {
        QuanLySachFragment fragment = new QuanLySachFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuanLySachBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = Room_DB.getInstance(requireContext()).getDAO();
        check = false;
        listMaLoaiSach = dao.getAllMaAndTenLoaiSach();
        arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,listMaLoaiSach);
        handleRecycler();
        addAction();
    }

    private void addAction() {
        binding.fragQuanLySachFloatAdd.setOnClickListener(v -> {
            if(listMaLoaiSach.isEmpty()){
                Toast.makeText(requireContext(), "Số lượng thể loại sách đang bị trống !", Toast.LENGTH_SHORT).show();
                return;
            }
            Dialog dialog = new Dialog(requireContext());
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_them_sua_sach);
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            //
            EditText ed_tenSach = dialog.findViewById(R.id.dialogThemSuaSach_ed_tenSach),
                    ed_giaCa = dialog.findViewById(R.id.dialogThemSuaSach_ed_giaSach),
                    ed_soTrang = dialog.findViewById(R.id.dialogThemSuaSach_ed_soTrang),
                    ed_tieuDe = dialog.findViewById(R.id.dialogThemSuaSach_ed_tieuDe);
            Spinner spinner = dialog.findViewById(R.id.dialogThemSuaSach_sp_loaiSach);
            Button btn_dongY = dialog.findViewById(R.id.dialogThemSuaSach_btn_dongY),
                    btn_huy = dialog.findViewById(R.id.dialogThemSuaSach_btn_huy);
            //
            spinner.setAdapter(arrayAdapter);
            //
            btn_dongY.setOnClickListener(e -> {
                String maLoaiSach = listMaLoaiSach.get(spinner.getSelectedItemPosition());
                String tieuDe = ed_tieuDe.getText().toString();
                String tenSach = ed_tenSach.getText().toString(),giaCa = ed_giaCa.getText().toString();
                if(tenSach.isEmpty() || giaCa.isEmpty() || tieuDe.isEmpty()){
                    Toast.makeText(requireContext(), "Thông tin bị trống !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!tieuDe.matches("\\d[a-zA-Z0-9]{4,19}")){
                    Toast.makeText(requireContext(), "Tiêu đề phải bắt đầu bằng 1 số và có độ dài min 5 max là 20 !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Sach obj = new Sach(Integer.parseInt(maLoaiSach.substring(0,maLoaiSach.indexOf("-"))),
                        Integer.parseInt(giaCa),Integer.parseInt(ed_soTrang.getText().toString()),tenSach,tieuDe);
                dao.insertOfSach(obj);
                list.add(dao.getObjNewOfSach());
                adapter.notifyItemInserted(list.size() - 1);
                dialog.cancel();
                Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            });
            btn_huy.setOnClickListener(e -> {
                dialog.cancel();
            });

            dialog.show();
        });

        binding.fragQuanLySachFloatAll.setOnClickListener(v -> {
            if(check){
                binding.fragQuanLySachFloatAdd.setVisibility(View.GONE);
                binding.fragQuanLySachFloatSearch.setVisibility(View.GONE);
                check = false;
            }else{
                binding.fragQuanLySachFloatAdd.setVisibility(View.VISIBLE);
                binding.fragQuanLySachFloatSearch.setVisibility(View.VISIBLE);
                check = true;
            }
        });
        binding.fragQuanLySachFloatSearch.setOnClickListener(v -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.dialog_search_sach);
            dialog.setCancelable(true);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //
            EditText ed_search = dialog.findViewById(R.id.dialogSearchSach_ed_search);
            Button btn_search = dialog.findViewById(R.id.dialogSearchSach_btn_search);
            btn_search.setOnClickListener(e -> {
                String soTrangSearch = ed_search.getText().toString();
                if(soTrangSearch.isEmpty()){
                    adapter.setData(dao.getAllOfSach());
                    return;
                }
                adapter.setData(dao.getSachWithSoTrang(Integer.parseInt(soTrangSearch)));
                dialog.cancel();
            });
            dialog.show();
        });
    }

    private void handleRecycler() {
        list = dao.getAllOfSach();
        adapter = new SachAdapter(this);
        binding.fragQuanLYSachRc.setAdapter(adapter);
        binding.fragQuanLYSachRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.setData(list);
    }

    @Override
    public void xoa(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận xoá");
        builder.setMessage("Việc này tương ứng sẽ xoá hết các dữ liệu liên quan");
        builder.setNegativeButton("Xoá", (dialogInterface, i) -> {
            Sach obj = list.get(position);
            dao.deleteOfSach(obj);
            list.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(requireContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
        });
        builder.setPositiveButton("Huỷ", (dialogInterface, i) -> {
        });
        builder.show();

    }

    @Override
    public void sua(int position) {
        Sach obj = list.get(position);
        Dialog dialog = new Dialog(requireContext());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_them_sua_sach);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        //
        EditText ed_tenSach = dialog.findViewById(R.id.dialogThemSuaSach_ed_tenSach),
                ed_giaCa = dialog.findViewById(R.id.dialogThemSuaSach_ed_giaSach),
                ed_soTrang = dialog.findViewById(R.id.dialogThemSuaSach_ed_soTrang),
                ed_tieuDe = dialog.findViewById(R.id.dialogThemSuaSach_ed_tieuDe);
        Spinner spinner = dialog.findViewById(R.id.dialogThemSuaSach_sp_loaiSach);
        Button btn_dongY = dialog.findViewById(R.id.dialogThemSuaSach_btn_dongY),
                btn_huy = dialog.findViewById(R.id.dialogThemSuaSach_btn_huy);
        //
        spinner.setAdapter(arrayAdapter);
        //
        int index = 0;
        for(String x : listMaLoaiSach){
            if(obj.getMaLoai() == Integer.parseInt(x.substring(0,x.indexOf("-")))){
                break;
            }
            index ++;
        }
        ed_tieuDe.setText(obj.getTieuDe());
        ed_soTrang.setText(String.valueOf(obj.getSoTrang()));
        spinner.setSelection(index);
        ed_giaCa.setText(String.valueOf(obj.getGiaThue()));
        ed_tenSach.setText(obj.getTenSach());
        //
        btn_dongY.setOnClickListener(e -> {
            String tieuDe = ed_tieuDe.getText().toString();
            String tenSach = ed_tenSach.getText().toString(),giaCa = ed_giaCa.getText().toString();
            if(tenSach.isEmpty() || giaCa.isEmpty() || tieuDe.isEmpty()){
                Toast.makeText(requireContext(), "Thông tin bị trống !", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean check = true;
            try{
                Integer.parseInt(tieuDe.substring(0,1));
                if(tieuDe.length() < 5 || tieuDe.length() > 20){
                    check = false;
                }
            }catch (Exception ex){
                check = false;
            }
            if(!check){
                Toast.makeText(requireContext(), "Tiêu đề phải bắt đầu bằng 1 số mà min là 5 max là 20 !", Toast.LENGTH_SHORT).show();
                return;
            }



            String maLoaiSach = listMaLoaiSach.get(spinner.getSelectedItemPosition());
            Sach obj2 = new Sach(Integer.parseInt(maLoaiSach.substring(0,maLoaiSach.indexOf("-"))),
                    Integer.parseInt(giaCa),Integer.parseInt(ed_soTrang.getText().toString()),tenSach,tieuDe);
            obj2.setMaSach(obj.getMaSach());
            dao.updateOfSach(obj2);
            list.set(position,obj2);
            adapter.notifyItemChanged(position);
            dialog.cancel();
        });
        btn_huy.setOnClickListener(e -> {
            dialog.cancel();
        });

        dialog.show();
    }
}