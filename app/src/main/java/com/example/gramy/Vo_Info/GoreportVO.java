package com.example.gramy.Vo_Info;

public class GoreportVO {
    String name;
    String mobile;
    String count;


    public GoreportVO(String name) {
        this.name = name;
    }

    public GoreportVO(String name, String mobile,String count) {
        this.name = name;
        this.mobile = mobile;
        this.count = count;
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

    public String getCount() {
        return count;
    }

    public void setCount(String mobile) {
        this.mobile = count;
    }

}


