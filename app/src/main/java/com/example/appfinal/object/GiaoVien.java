package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GiaoVien implements Parcelable {
    private String HoTen, Email;
    private Integer Tuoi;
    private String SoDienThoai, DiaChi;
    private ArrayList<Lop> DSLopChuNhiem;

    public GiaoVien() {
    }

    public GiaoVien(String hoTen, String email, Integer tuoi, String soDienThoai, String diaChi, ArrayList<Lop> DSLopChuNhiem) {
        HoTen = hoTen;
        Email = email;
        Tuoi = tuoi;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        this.DSLopChuNhiem = DSLopChuNhiem;
    }

    protected GiaoVien(Parcel in) {
        HoTen = in.readString();
        Email = in.readString();
        if (in.readByte() == 0) {
            Tuoi = null;
        } else {
            Tuoi = in.readInt();
        }
        SoDienThoai = in.readString();
        DiaChi = in.readString();
        DSLopChuNhiem = in.createTypedArrayList(Lop.CREATOR);
    }

    public static final Creator<GiaoVien> CREATOR = new Creator<GiaoVien>() {
        @Override
        public GiaoVien createFromParcel(Parcel in) {
            return new GiaoVien(in);
        }

        @Override
        public GiaoVien[] newArray(int size) {
            return new GiaoVien[size];
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
        if (Tuoi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Tuoi);
        }
        dest.writeString(SoDienThoai);
        dest.writeString(DiaChi);
        dest.writeTypedList(DSLopChuNhiem);
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

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public ArrayList<Lop> getDSLopChuNhiem() {
        return DSLopChuNhiem;
    }

    public void setDSLopChuNhiem(ArrayList<Lop> DSLopChuNhiem) {
        this.DSLopChuNhiem = DSLopChuNhiem;
    }

    public static Creator<GiaoVien> getCREATOR() {
        return CREATOR;
    }
}
