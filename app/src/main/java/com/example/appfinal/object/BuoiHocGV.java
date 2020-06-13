package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class BuoiHocGV implements Parcelable {
    private String Thu, Buoi, ThoiGian, Lop;

    public BuoiHocGV() {
        Thu = "";
        Buoi = "";
        ThoiGian = "";
        Lop = "";
    }

    public BuoiHocGV(String thu, String buoi, String thoiGian, String lop) {
        Thu = thu;
        Buoi = buoi;
        ThoiGian = thoiGian;
        Lop = lop;
    }

    protected BuoiHocGV(Parcel in) {
        Thu = in.readString();
        Buoi = in.readString();
        ThoiGian = in.readString();
        Lop = in.readString();
    }

    public static final Creator<BuoiHocGV> CREATOR = new Creator<BuoiHocGV>() {
        @Override
        public BuoiHocGV createFromParcel(Parcel in) {
            return new BuoiHocGV(in);
        }

        @Override
        public BuoiHocGV[] newArray(int size) {
            return new BuoiHocGV[size];
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
        dest.writeString(Lop);
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

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    public static Creator<BuoiHocGV> getCREATOR() {
        return CREATOR;
    }
}
