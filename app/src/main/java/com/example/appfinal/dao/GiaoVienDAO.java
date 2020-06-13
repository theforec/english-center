package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.main.LoginFragment;
import com.example.appfinal.fragment.phuHuynh.PH_ChatFragment;
import com.example.appfinal.object.GiaoVien;
import com.example.appfinal.object.Lop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class GiaoVienDAO {
    private DatabaseReference myRef;
    public static final int GET_GIAOVIEN = 3;
    public static final int GET_EMAIL_GIAOVIEN = 32156;

    public GiaoVienDAO() {
        myRef = FirebaseDatabase.getInstance().getReference("GiaoVien");
    }

    public void getGiaoVien(final String email) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GiaoVien giaoVien = new GiaoVien();

                    for (DataSnapshot dataChild : data.getChildren()
                    ) {
                        if (dataChild.getKey().equalsIgnoreCase("email")) {
                            giaoVien.setEmail(dataChild.getValue(String.class));
                            if (!giaoVien.getEmail().equalsIgnoreCase(email)) {
                                break;
                            }
                        }

                        if (dataChild.getKey().equalsIgnoreCase("diaChi")) {
                            giaoVien.setDiaChi(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("dslopChuNhiem")) {
                            ArrayList<Lop> dsLopCN = new ArrayList<>();
                            for (DataSnapshot dsLop : dataChild.getChildren()
                            ) {
                                Lop lop = dsLop.getValue(Lop.class);
                                dsLopCN.add(lop);
                            }
                            giaoVien.setDSLopChuNhiem(dsLopCN);
                        }
                        if (dataChild.getKey().equalsIgnoreCase("hoTen")) {
                            giaoVien.setHoTen(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equalsIgnoreCase("soDienThoai")) {
                            giaoVien.setSoDienThoai(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equalsIgnoreCase("tuoi")) {
                            giaoVien.setTuoi(dataChild.getValue(Integer.class));
                        }
                    }

                    if (giaoVien.getEmail().equalsIgnoreCase(email)) {
                        if (LoginFragment.handler != null) {
                            Message msg = new Message();
                            msg.what = GET_GIAOVIEN;
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("gv", giaoVien);
                            bundle.putString("key", data.getKey());
                            msg.setData(bundle);
                            msg.setTarget(LoginFragment.handler);
                            msg.sendToTarget();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getEmailGvcn(final String ten_gvcn) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GiaoVien giaoVien = data.getValue(GiaoVien.class);
                    if (giaoVien.getHoTen().equals(ten_gvcn)) {
                        Message msg = new Message();
                        msg.what = GET_EMAIL_GIAOVIEN;
                        Bundle bundle = new Bundle();
                        bundle.putString("email_gvcn", giaoVien.getEmail());
                        msg.setData(bundle);
                        msg.setTarget(PH_ChatFragment.handler);
                        msg.sendToTarget();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
