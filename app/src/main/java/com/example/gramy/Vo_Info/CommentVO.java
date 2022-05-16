package com.example.gramy.Vo_Info;

public class CommentVO {
    private int tb_a_seq;
    private int ar_seq;
    private String ar_content;
    private String user_id;
    private String user_name;
    private String ar_date;

    public CommentVO(int tb_a_seq, int ar_seq, String ar_content, String user_id, String user_name, String ar_date) {
        this.tb_a_seq = tb_a_seq;
        this.ar_seq = ar_seq;
        this.ar_content = ar_content;
        this.user_id = user_id;
        this.user_name = user_name;
        this.ar_date = ar_date;
    }

    public int getTb_a_seq() {
        return tb_a_seq;
    }

    public void setTb_a_seq(int tb_a_seq) {
        this.tb_a_seq = tb_a_seq;
    }

    public int getAr_seq() {
        return ar_seq;
    }

    public void setAr_seq(int ar_seq) {
        this.ar_seq = ar_seq;
    }

    public String getAr_content() {
        return ar_content;
    }

    public void setAr_content(String ar_content) {
        this.ar_content = ar_content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAr_date() {
        return ar_date;
    }

    public void setAr_date(String ar_date) {
        this.ar_date = ar_date;
    }
}
