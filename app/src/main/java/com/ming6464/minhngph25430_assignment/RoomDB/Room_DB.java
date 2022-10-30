package com.ming6464.minhngph25430_assignment.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ming6464.minhngph25430_assignment.Converters;
import com.ming6464.minhngph25430_assignment.DAO.DAO;
import com.ming6464.minhngph25430_assignment.model.LoaiSach;
import com.ming6464.minhngph25430_assignment.model.PhieuMuon;
import com.ming6464.minhngph25430_assignment.model.Sach;
import com.ming6464.minhngph25430_assignment.model.ThanhVien;
import com.ming6464.minhngph25430_assignment.model.ThuThu;

@TypeConverters({Converters.class})
@Database(entities = {ThanhVien.class, ThuThu.class, LoaiSach.class, PhieuMuon.class, Sach.class},version = 1)
public abstract class Room_DB extends RoomDatabase {
    public static final String NAME_DB = "ROOM.DB";
    public static Room_DB instance;

    public static Room_DB getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context,Room_DB.class,NAME_DB).allowMainThreadQueries().build();
        return instance;
    }
    public abstract DAO getDAO();

}
