package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectImage implements Parcelable {
    String TenAnh, LinkAnh;

    public SelectImage() {
    }

    public SelectImage(String tenAnh, String linkAnh) {
        TenAnh = tenAnh;
        LinkAnh = linkAnh;
    }

    protected SelectImage(Parcel in) {
        TenAnh = in.readString();
        LinkAnh = in.readString();
    }

    public static final Creator<SelectImage> CREATOR = new Creator<SelectImage>() {
        @Override
        public SelectImage createFromParcel(Parcel in) {
            return new SelectImage(in);
        }

        @Override
        public SelectImage[] newArray(int size) {
            return new SelectImage[size];
        }
    };

    public String getTenAnh() {
        return TenAnh;
    }

    public void setTenAnh(String tenAnh) {
        TenAnh = tenAnh;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TenAnh);
        dest.writeString(LinkAnh);
    }
}
