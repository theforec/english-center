package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Lop implements Parcelable {
    private String TenLop;

    public Lop() {
    }

    public Lop(String tenLop) {
        TenLop = tenLop;
    }

    protected Lop(Parcel in) {
        TenLop = in.readString();
    }

    public static Creator<Lop> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<Lop> CREATOR = new Creator<Lop>() {
        @Override
        public Lop createFromParcel(Parcel in) {
            return new Lop(in);
        }

        @Override
        public Lop[] newArray(int size) {
            return new Lop[size];
        }
    };

    public String getTenLop() {
        return TenLop;
    }

    public void setTenLop(String tenLop) {
        TenLop = tenLop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TenLop);
    }
}
