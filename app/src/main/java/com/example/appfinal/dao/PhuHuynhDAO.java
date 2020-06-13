package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.main.LoginFragment;
import com.example.appfinal.object.PhuHuynh;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class PhuHuynhDAO {
    private DatabaseReference myRef;
    public static final int GET_PHUHUYNH = 2;

    public PhuHuynhDAO() {
        myRef = FirebaseDatabase.getInstance().getReference("PhuHuynh");
    }
    public void getPhuHuynh(final String email){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    PhuHuynh phuHuynh = data.getValue(PhuHuynh.class);

                    if (phuHuynh.getEmail().equalsIgnoreCase(email)){
                        Message msg = new Message();
                        msg.what = GET_PHUHUYNH;
                        msg.setTarget(LoginFragment.handler);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("ph",phuHuynh);
                        bundle.putString("key",data.getKey());
                        msg.setData(bundle);
                        msg.sendToTarget();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
