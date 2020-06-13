package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.giaoVien.GV_LearnFragment;
import com.example.appfinal.fragment.phuHuynh.PH_LearnFragment;
import com.example.appfinal.object.SelectImage;
import com.example.appfinal.object.Test;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class TestDAO {
    private DatabaseReference myRef;
    private String ten_lop;
    public static final int GET_LIST_TEST = 321;
    public static final int GET_LIST_TEST_PH = 322;

    public TestDAO(String ten_lop) {
        this.ten_lop = ten_lop;
        myRef = FirebaseDatabase.getInstance().getReference("BaiKiemTra");
    }

    public void getListTest() {
        final ArrayList<Test> arrTest = new ArrayList<>();
        myRef.child(ten_lop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Test test = new Test();
                    for (DataSnapshot dataChild : data.getChildren()) {
                        if (dataChild.getKey().equals("arrImages")) {
                            ArrayList<SelectImage> arrImages = new ArrayList<>();
                            for (DataSnapshot dsImage : dataChild.getChildren()) {
                                SelectImage image = dsImage.getValue(SelectImage.class);
                                arrImages.add(image);
                            }
                            test.setArrImages(arrImages);
                        }
                        if (dataChild.getKey().equals("date"))
                            test.setDate(dataChild.getValue(String.class));
                        if (dataChild.getKey().equals("lop"))
                            test.setLop(dataChild.getValue(String.class));
                        if (dataChild.getKey().equals("testName"))
                            test.setTestName(dataChild.getValue(String.class));
                        if (dataChild.getKey().equals("topic"))
                            test.setTopic(dataChild.getValue(String.class));
                    }
                    arrTest.add(test);
                }
                if (GV_LearnFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_TEST;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("test", arrTest);
                    msg.setData(bundle);
                    msg.setTarget(GV_LearnFragment.handler);
                    msg.sendToTarget();
                }
                if (PH_LearnFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_TEST_PH;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("test2", arrTest);
                    msg.setData(bundle);
                    msg.setTarget(PH_LearnFragment.handler);
                    msg.sendToTarget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
