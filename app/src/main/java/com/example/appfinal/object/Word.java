package com.example.appfinal.object;

public class Word {
    private String Tu, LinkAnh;

    public Word() {
    }

    public Word(String tu, String linkAnh) {
        Tu = tu;
        LinkAnh = linkAnh;
    }

    public String getTu() {
        return Tu;
    }

    public void setTu(String tu) {
        Tu = tu;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }
}
