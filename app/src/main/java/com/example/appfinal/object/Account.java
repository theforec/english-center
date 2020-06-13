package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private String Email, Password;
    private Integer Role; //role 1: ph, role 0: gv
    public static final int ROLE_GV = 0;
    public static final int ROLE_PH = 1;
    public Account() {
    }

    public Account(String email, String password, Integer role) {
        Email = email;
        Password = password;
        Role = role;
    }

    protected Account(Parcel in) {
        Email = in.readString();
        Password = in.readString();
        if (in.readByte() == 0) {
            Role = null;
        } else {
            Role = in.readInt();
        }
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getRole() {
        return Role;
    }

    public void setRole(Integer role) {
        Role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(Password);
        if(Role==null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Role);
        }

    }
}

