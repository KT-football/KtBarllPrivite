package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/15.
 */
public class LeaguesData {
    public String response;
    public ArrayList<Leagues> leagues = new ArrayList<>();

    @Override
    public String toString() {
        return "LeaguesData{" +
                "response='" + response + '\'' +
                ", leagues=" + leagues +
                '}';
    }
}
