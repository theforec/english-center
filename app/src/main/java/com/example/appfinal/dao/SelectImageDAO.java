package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.giaoVien.GV_TestFragment;
import com.example.appfinal.fragment.phuHuynh.PH_TestFragment;
import com.example.appfinal.object.SelectImage;
import com.example.appfinal.fragment.giaoVien.GV_LessonFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SelectImageDAO {
    private DatabaseReference myRef;
    public static final int GET_LIST_IMAGE = 1;
    public static final int GET_LIST_IMAGE_PH = 197;

    public SelectImageDAO() {
        myRef = FirebaseDatabase.getInstance().getReference("images");
    }

    public void getListImage() {
        final ArrayList<SelectImage> arrImages = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    SelectImage image = data.getValue(SelectImage.class);
                    arrImages.add(image);
                }

                if (GV_LessonFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_IMAGE;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("images", arrImages);
                    msg.setData(bundle);
                    msg.setTarget(GV_LessonFragment.handler);
                    msg.sendToTarget();
                }
                if (GV_TestFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_IMAGE;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("images", arrImages);
                    msg.setData(bundle);
                    msg.setTarget(GV_TestFragment.handler);
                    msg.sendToTarget();
                }
                if (PH_TestFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_IMAGE_PH;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("images", arrImages);
                    msg.setData(bundle);
                    msg.setTarget(PH_TestFragment.handler);
                    msg.sendToTarget();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
