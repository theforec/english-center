package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Status implements Parcelable {
    private String HocVien, TinhHinhHocTap;

    public Status() {
    }

    public Status(String hocVien, String tinhHinhHocTap) {
        HocVien = hocVien;
        TinhHinhHocTap = tinhHinhHocTap;
    }

    protected Status(Parcel in) {
        HocVien = in.readString();
        TinhHinhHocTap = in.readString();
    }

    public static Creator<Status> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public String getHocVien() {
        return HocVien;
    }

    public void setHocVien(String hocVien) {
        HocVien = hocVien;
    }

    public String getTinhHinhHocTap() {
        return TinhHinhHocTap;
    }

    public void setTinhHinhHocTap(String tinhHinhHocTap) {
        TinhHinhHocTap = tinhHinhHocTap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HocVien);
        dest.writeString(TinhHinhHocTap);
    }
}
