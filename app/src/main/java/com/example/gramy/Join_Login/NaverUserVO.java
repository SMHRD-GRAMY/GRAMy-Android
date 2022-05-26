package com.example.gramy.Join_Login;

import java.io.Serializable;

public class NaverUserVO implements Serializable {
    private String name;
    private String email;

    public NaverUserVO(){}

    public NaverUserVO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NaverUserVO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
