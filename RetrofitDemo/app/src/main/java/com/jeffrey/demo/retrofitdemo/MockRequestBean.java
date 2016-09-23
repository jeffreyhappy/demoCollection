package com.jeffrey.demo.retrofitdemo;

/**
 * Created by Li on 2016/9/23.
 */

public class MockRequestBean {
    private String username;
    private String  password;

    public MockRequestBean(String name,String age){
        this.username = name;
        this.password = age;
    }
}
