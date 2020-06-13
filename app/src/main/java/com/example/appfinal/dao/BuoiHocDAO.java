package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.giaoVien.GV_TimetableFragment;
import com.example.appfinal.fragment.phuHuynh.PH_TimetableFragment;
import com.example.appfinal.object.BuoiHoc;
import com.example.appfinal.object.BuoiHocGV;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class BuoiHocDAO {

    private DatabaseReference myRef;
    public static final int GET_BUOIHOC_PH = 6654, GET_BUOIHOC_GV = 6655;

    public BuoiHocDAO() {
        myRef = FirebaseDatabase.getInstance().getReference("ThoiKhoaBieu");
    }

    public void getBuoiHocPH(String ten_lop) {
        final ArrayList<BuoiHoc> arrBuoiHoc = new ArrayList<>();
        myRef.child("PhuHuynh").child(ten_lop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    BuoiHoc buoiHoc = data.getValue(BuoiHoc.class);
                    arrBuoiHoc.add(buoiHoc);
                }
                if (PH_TimetableFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_BUOIHOC_PH;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("buoihoc", arrBuoiHoc);
                    msg.setData(bundle);
                    msg.setTarget(PH_TimetableFragment.handler);
                    msg.sendToTarget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getBuoiHocGV(String name) {
        final ArrayList<BuoiHocGV> arrBuoiHoc = new ArrayList<>();
        myRef.child("GiaoVien").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    BuoiHocGV buoiHoc = data.getValue(BuoiHocGV.class);
                    arrBuoiHoc.add(buoiHoc);
                }
                if (GV_TimetableFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_BUOIHOC_GV;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("buoihocgv", arrBuoiHoc);
                    msg.setData(bundle);
                    msg.setTarget(GV_TimetableFragment.handler);
                    msg.sendToTarget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
