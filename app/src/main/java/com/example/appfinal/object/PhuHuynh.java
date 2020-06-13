package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class PhuHuynh implements Parcelable {

    private String Email, HoTenHS, HoTenPH;
    private Integer Tuoi;
    private String SoDienThoai,Lop,GVCN;

    public PhuHuynh() {
    }

    public PhuHuynh(String email, String hoTenHS, String hoTenPH, Integer tuoi, String soDienThoai, String lop, String GVCN) {
        Email = email;
        HoTenHS = hoTenHS;
        HoTenPH = hoTenPH;
        Tuoi = tuoi;
        SoDienThoai = soDienThoai;
        Lop = lop;
        this.GVCN = GVCN;
    }

    protected PhuHuynh(Parcel in) {
        Email = in.readString();
        HoTenHS = in.readString();
        HoTenPH = in.readString();
        if (in.readByte() == 0) {
            Tuoi = null;
        } else {
            Tuoi = in.readInt();
        }
        SoDienThoai = in.readString();
        Lop = in.readString();
        GVCN = in.readString();
    }

    public static final Creator<PhuHuynh> CREATOR = new Creator<PhuHuynh>() {
        @Override
        public PhuHuynh createFromParcel(Parcel in) {
            return new PhuHuynh(in);
        }

        @Override
        public PhuHuynh[] newArray(int size) {
            return new PhuHuynh[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(HoTenHS);
        dest.writeString(HoTenPH);
        if (Tuoi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Tuoi);
        }
        dest.writeString(SoDienThoai);
        dest.writeString(Lop);
        dest.writeString(GVCN);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHoTenHS() {
        return HoTenHS;
    }

    public void setHoTenHS(String hoTenHS) {
        HoTenHS = hoTenHS;
    }

    public String getHoTenPH() {
        return HoTenPH;
    }

    public void setHoTenPH(String hoTenPH) {
        HoTenPH = hoTenPH;
    }

    public Integer getTuoi() {
        return Tuoi;
    }

    public void setTuoi(Integer tuoi) {
        Tuoi = tuoi;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    public String getGVCN() {
        return GVCN;
    }

    public void setGVCN(String GVCN) {
        this.GVCN = GVCN;
    }

    public static Creator<PhuHuynh> getCREATOR() {
        return CREATOR;
    }
}
