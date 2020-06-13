package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String HoTen, Email;

    public User() {
        HoTen = "";
        Email = "";
    }

    public User(String hoTen, String email) {
        HoTen = hoTen;
        Email = email;
    }

    protected User(Parcel in) {
        HoTen = in.readString();
        Email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HoTen);
        dest.writeString(Email);
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }
}
