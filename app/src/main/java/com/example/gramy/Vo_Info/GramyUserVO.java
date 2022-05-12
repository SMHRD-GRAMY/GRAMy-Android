package com.example.gramy.Vo_Info;

public class GramyUserVO {

    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_phone;
    private String user_addr;
    private String user_role;
    private String user_joindate;
    private String user_gender;

    public GramyUserVO(){}

    public GramyUserVO(String user_id, String user_pw, String user_name, String user_phone, String user_addr, String user_role, String user_joindate, String user_gender) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_addr = user_addr;
        this.user_role = user_role;
        this.user_joindate = user_joindate;
        this.user_gender = user_gender;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_addr() {
        return user_addr;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_joindate() {
        return user_joindate;
    }

    public void setUser_joindate(String user_joindate) {
        this.user_joindate = user_joindate;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    @Override
    public String toString() {
        return "GramyUserVO{" +
                ", user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_addr='" + user_addr + '\'' +
                ", user_role='" + user_role + '\'' +
                ", user_joindate='" + user_joindate + '\'' +
                ", user_gender='" + user_gender + '\'' +
                '}';
    }
}
