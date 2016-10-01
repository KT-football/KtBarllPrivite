package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/14.
 */
public class CimmentsData {

    public String response;
    public ArrayList<Comments> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "CimmentsData{" +
                "response='" + response + '\'' +
                ", comments=" + comments +
                '}';
    }
}
