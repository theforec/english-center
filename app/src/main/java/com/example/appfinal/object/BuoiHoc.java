package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class BuoiHoc implements Parcelable {
    private String Thu, Buoi, ThoiGian;

    public BuoiHoc() {
        Thu = "";
        Buoi = "";
        ThoiGian = "";
    }

    public BuoiHoc(String thu, String buoi, String thoiGian) {
        Thu = thu;
        Buoi = buoi;
        ThoiGian = thoiGian;
    }

    protected BuoiHoc(Parcel in) {
        Thu = in.readString();
        Buoi = in.readString();
        ThoiGian = in.readString();
    }

    public static final Creator<BuoiHoc> CREATOR = new Creator<BuoiHoc>() {
        @Override
        public BuoiHoc createFromParcel(Parcel in) {
            return new BuoiHoc(in);
        }

        @Override
        public BuoiHoc[] newArray(int size) {
            return new BuoiHoc[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Thu);
        dest.writeString(Buoi);
        dest.writeString(ThoiGian);
    }

    public String getThu() {
        return Thu;
    }

    public void setThu(String thu) {
        Thu = thu;
    }

    public String getBuoi() {
        return Buoi;
    }

    public void setBuoi(String buoi) {
        Buoi = buoi;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public static Creator<BuoiHoc> getCREATOR() {
        return CREATOR;
    }
}
