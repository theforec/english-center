package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.main.LoginFragment;
import com.example.appfinal.object.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class AccountDAO {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    public static final int GET_ACCOUNT = 1;
    public static final int GET_ACCOUNT_ERROR = 0;

    public AccountDAO() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
    }

    public void getAccount(final String email, final String password) {
        myRef.child("/Account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean check = false;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account account = data.getValue(Account.class);
                    if (account.getEmail().equalsIgnoreCase(email) && account.getPassword().equals(password)) {
                        if (LoginFragment.handler != null) {
                            Message msg = new Message();
                            msg.what = GET_ACCOUNT;
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("account", account);
                            msg.setData(bundle);
                            msg.setTarget(LoginFragment.handler); //
                            msg.sendToTarget();
                            check = true;
                        }
                        break;
                    }
                }
                if(!check){
                    if (LoginFragment.handler!=null){
                        Message msg = new Message();
                        msg.what = GET_ACCOUNT_ERROR;
                        msg.setTarget(LoginFragment.handler); //
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
