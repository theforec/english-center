package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.giaoVien.GV_LessonFragment;
import com.example.appfinal.fragment.giaoVien.GV_UserFragment;
import com.example.appfinal.fragment.giaoVien.GV_UserUserFragment;
import com.example.appfinal.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class HocVienDAO {
    private DatabaseReference myRef;
    public static final int GET_LIST_HOCVIEN = 20;
    public static final int GET_LIST_HOCVIEN_FAIL = 21;
    public static final int GET_LIST_USER = 518;
    public static final int GET_LIST_USER_FAIL = 519;

    public HocVienDAO() {
        myRef = FirebaseDatabase.getInstance().getReference("DSHocVien");
    }

    public void getListHocVien(String ten_lop) {
        final ArrayList<String> arrHocVien = new ArrayList<>();
        myRef.child(ten_lop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    String hocvien = user.getHoTen();
                    arrHocVien.add(hocvien);
                }
                if (GV_LessonFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_HOCVIEN;
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("hocvien", arrHocVien);
                    msg.setData(bundle);
                    msg.setTarget(GV_LessonFragment.handler);
                    msg.sendToTarget();
                } else {
                    Message msg = new Message();
                    msg.what = GET_LIST_HOCVIEN_FAIL;
                    msg.setTarget(GV_LessonFragment.handler);
                    msg.sendToTarget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getListUser(String ten_lop) {
        final ArrayList<User> arrUser = new ArrayList<>();
        myRef.child(ten_lop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    arrUser.add(user);
                }
                if (GV_UserUserFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_USER;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("users", arrUser);
                    msg.setData(bundle);
                    msg.setTarget(GV_UserUserFragment.handler);
                    msg.sendToTarget();
                }  else {
                    Message msg = new Message();
                    msg.what = GET_LIST_USER_FAIL;
                    msg.setTarget(GV_UserUserFragment.handler);
                    msg.sendToTarget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
