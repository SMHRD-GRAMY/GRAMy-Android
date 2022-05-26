package com.example.gramy.Vo_Info;

public class GoreportVO {
    private int shelf_seq;
    private String shelf_name;
    private String user_id;

    public GoreportVO(int shelf_seq, String shelf_name, String user_id) {
        this.shelf_seq = shelf_seq;
        this.shelf_name = shelf_name;
        this.user_id = user_id;
    }

    public int getShelf_seq() {
        return shelf_seq;
    }

    public void setShelf_seq(int shelf_seq) {
        this.shelf_seq = shelf_seq;
    }

    public String getShelf_name() {
        return shelf_name;
    }

    public void setShelf_name(String shelf_name) {
        this.shelf_name = shelf_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}


