package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/18.
 */
public class LocationData {
    public String response;
    public ArrayList<Location> games = new ArrayList<>();
//    "followers": 6,
    public int followers;
//            "scores": 369,
    public int scores;
//            "nickname": "姜涛",
    public String nickname;
//            "fans": 1,
    public int fans;
//            "grade": 2,
    public int grade;
//            "avatar": "/system/user_profiles/avatars/000/053/697/original/0?1459133512"
    public String avatar;

    @Override
    public String toString() {
        return "LocationData{" +
                "response='" + response + '\'' +
                ", games=" + games +
                ", followers=" + followers +
                ", scores=" + scores +
                ", nickname='" + nickname + '\'' +
                ", fans=" + fans +
                ", grade=" + grade +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
