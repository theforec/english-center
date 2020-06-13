package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Test implements Parcelable {
    private String TestName, Date, Lop, Topic;
    private ArrayList<SelectImage> arrImages;

    public Test() {
        TestName = "Test: ";
        Date = "Chọn ngày";
        Lop = "Chọn lớp";
        Topic = "";
        this.arrImages = new ArrayList<>();
    }

    public Test(String testName, String date, String lop, String topic, ArrayList<SelectImage> arrImages) {
        TestName = testName;
        Date = date;
        Lop = lop;
        Topic = topic;
        this.arrImages = arrImages;
    }

    protected Test(Parcel in) {
        TestName = in.readString();
        Date = in.readString();
        Lop = in.readString();
        Topic = in.readString();
        arrImages = in.createTypedArrayList(SelectImage.CREATOR);
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TestName);
        dest.writeString(Date);
        dest.writeString(Lop);
        dest.writeString(Topic);
        dest.writeTypedList(arrImages);
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public ArrayList<SelectImage> getArrImages() {
        return arrImages;
    }

    public void setArrImages(ArrayList<SelectImage> arrImages) {
        this.arrImages = arrImages;
    }

    public static Creator<Test> getCREATOR() {
        return CREATOR;
    }
}
