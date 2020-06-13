package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Lesson implements Parcelable {
    private String Lesson, Ngay, Lop, Book, Topic;

    private ArrayList<Word> DanhSachTu;
    private String HoatDong, LinkVideo, BaiTap;
    private ArrayList<Status> TinhHinhLopHoc;

    public Lesson() {
        Lesson = "Lesson: ";
        Ngay = "Chọn ngày";
        Lop = "Chọn lớp";
        Book = "";
        Topic = "";
        DanhSachTu = new ArrayList<>();
        HoatDong = "";
        LinkVideo = "";
        BaiTap = "";
        TinhHinhLopHoc = new ArrayList<>();
    }

    public Lesson(String lesson, String ngay, String lop, String book, String topic, ArrayList<Word> danhSachTu, String hoatDong, String linkVideo, String baiTap, ArrayList<Status> tinhHinhLopHoc) {
        Lesson = lesson;
        Ngay = ngay;
        Lop = lop;
        Book = book;
        Topic = topic;
        DanhSachTu = danhSachTu;
        HoatDong = hoatDong;
        LinkVideo = linkVideo;
        BaiTap = baiTap;
        TinhHinhLopHoc = tinhHinhLopHoc;
    }

    protected Lesson(Parcel in) {
        Lesson = in.readString();
        Ngay = in.readString();
        Lop = in.readString();
        Book = in.readString();
        Topic = in.readString();
        HoatDong = in.readString();
        LinkVideo = in.readString();
        BaiTap = in.readString();
        TinhHinhLopHoc = in.createTypedArrayList(Status.CREATOR);
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Lesson);
        dest.writeString(Ngay);
        dest.writeString(Lop);
        dest.writeString(Book);
        dest.writeString(Topic);
        dest.writeString(HoatDong);
        dest.writeString(LinkVideo);
        dest.writeString(BaiTap);
        dest.writeTypedList(TinhHinhLopHoc);
    }

    public String getLesson() {
        return Lesson;
    }

    public void setLesson(String lesson) {
        Lesson = lesson;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    public String getBook() {
        return Book;
    }

    public void setBook(String book) {
        Book = book;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public ArrayList<Word> getDanhSachTu() {
        return DanhSachTu;
    }

    public void setDanhSachTu(ArrayList<Word> danhSachTu) {
        DanhSachTu = danhSachTu;
    }

    public String getHoatDong() {
        return HoatDong;
    }

    public void setHoatDong(String hoatDong) {
        HoatDong = hoatDong;
    }

    public String getLinkVideo() {
        return LinkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        LinkVideo = linkVideo;
    }

    public String getBaiTap() {
        return BaiTap;
    }

    public void setBaiTap(String baiTap) {
        BaiTap = baiTap;
    }

    public ArrayList<Status> getTinhHinhLopHoc() {
        return TinhHinhLopHoc;
    }

    public void setTinhHinhLopHoc(ArrayList<Status> tinhHinhLopHoc) {
        TinhHinhLopHoc = tinhHinhLopHoc;
    }

    public static Creator<com.example.appfinal.object.Lesson> getCREATOR() {
        return CREATOR;
    }
}
