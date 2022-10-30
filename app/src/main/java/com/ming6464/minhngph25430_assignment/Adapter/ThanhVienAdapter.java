package com.ming6464.minhngph25430_assignment.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.minhngph25430_assignment.R;
import com.ming6464.minhngph25430_assignment.model.LoaiSach;
import com.ming6464.minhngph25430_assignment.model.ThanhVien;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.MyViewHolder>{
    private List<ThanhVien> list;
    private OnAction action;
    private View view;

    public ThanhVienAdapter(OnAction action){
        this.action = action;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanh_vien,parent,false);
        return new MyViewHolder(view);
    }
    public void setData(List<ThanhVien> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ThanhVien obj = list.get(position);
        String maTV = String.valueOf(obj.getMaTV());
        if(obj.getMaTV() < 10)
            maTV = "0" + obj.getMaTV();
        holder.maTV.setText(maTV);
        holder.namSinh.setText("Năm Sinh: " + obj.getNamSinh());
        holder.hoTen.setText(obj.getHoTen());
        view.setOnClickListener(view -> action.actionSua(holder.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            action.actionXoa(holder.getAdapterPosition());
            return true;
        });
    }
    public interface OnAction{
        void actionSua(int position);
        void actionXoa(int position);
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView maTV;
        private final TextView hoTen;
        private final TextView namSinh;
        public MyViewHolder(@NonNull View it) {
            super(it);
            maTV = it.findViewById(R.id.itemThanhVien_tv_maTV);
            hoTen = it.findViewById(R.id.itemThanhVien_tv_hoTen);
            namSinh = it.findViewById(R.id.itemThanhVien_tv_namSinh);
        }
    }
}
