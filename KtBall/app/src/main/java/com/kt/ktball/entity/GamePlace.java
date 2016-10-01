package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by ww on 2016/4/4.
 */
public class  GamePlace {

    public String response;
    public ArrayList<Games> games;

    @Override
    public String toString() {
        return "GamePlace{" +
                "response='" + response + '\'' +
                ", games=" + games +
                '}';
    }
}
