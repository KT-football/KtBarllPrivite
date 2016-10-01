package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/12.
 */
public class MyFirendData {
//    response: success
    public String response;
//    followers: [ + ]
    public ArrayList<Users> followers = new ArrayList<>();

    @Override
    public String toString() {
        return "MyFirendData{" +
                "response='" + response + '\'' +
                ", followers=" + followers +
                '}';
    }
}
