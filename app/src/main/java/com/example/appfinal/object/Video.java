package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
    private String LinkVideo,TenVideo ;

    public Video() {
    }

    public Video(String linkVideo, String tenVideo) {
        LinkVideo = linkVideo;
        TenVideo = tenVideo;
    }

    protected Video(Parcel in) {
        LinkVideo = in.readString();
        TenVideo = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getLinkVideo() {
        return LinkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        LinkVideo = linkVideo;
    }

    public String getTenVideo() {
        return TenVideo;
    }

    public void setTenVideo(String tenVideo) {
        TenVideo = tenVideo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(LinkVideo);
        dest.writeString(TenVideo);
    }
}
