package com.example.appfinal.object;

public class SelectVideo {
    private String TenVideo,LinkVideo;

    public SelectVideo() {
    }

    public SelectVideo(String tenVideo, String linkVideo) {
        TenVideo = tenVideo;
        LinkVideo = linkVideo;
    }

    public String getTenVideo() {
        return TenVideo;
    }

    public void setTenVideo(String tenVideo) {
        TenVideo = tenVideo;
    }

    public String getLinkVideo() {
        return LinkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        LinkVideo = linkVideo;
    }
}
