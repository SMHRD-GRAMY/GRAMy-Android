package com.example.gramy.Vo_Info;

public class GomyshelfVO {
    String name;
    String mobile;


    public GomyshelfVO(String name) {
        this.name = name;
    }

    public GomyshelfVO(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
