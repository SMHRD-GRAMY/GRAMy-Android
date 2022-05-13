package com.example.gramy.Vo_Info;

public class BoardVO {
    private int tb_a_seq; // 게시글 번호
    private String tb_a_title; // 게시글 이름
    private String tb_a_content; // 게시글 내용
    private String tb_a_date; // 게시글 작성일
    private String user_id; // 작성자 아이디
    private String user_name; // 작성자 이름

    public BoardVO(){}


    public BoardVO(int tb_a_seq, String tb_a_title, String tb_a_content, String tb_a_date, String user_id, String user_name) {
        this.tb_a_seq = tb_a_seq;
        this.tb_a_title = tb_a_title;
        this.tb_a_content = tb_a_content;
        this.tb_a_date = tb_a_date;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public String getTb_a_content() {
        return tb_a_content;
    }

    public void setTb_a_content(String tb_a_content) {
        this.tb_a_content = tb_a_content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTb_a_date() {
        return tb_a_date;
    }

    public void setTb_a_date(String tb_a_date) {
        this.tb_a_date = tb_a_date;
    }

    public int getTb_a_seq() {
        return tb_a_seq;
    }

    public void setTb_a_seq(int tb_a_seq) {
        this.tb_a_seq = tb_a_seq;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTb_a_title() {
        return tb_a_title;
    }

    public void setTb_a_title(String tb_a_title) {
        this.tb_a_title = tb_a_title;
    }
}
