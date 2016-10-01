package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/12.
 */
public class RankingData {
//    response: success
    public String response;
//    nickname: 姜涛
    public String nickname;
//    avatar: /system/user_profiles/avatars/000/053/697/original/0?1459133512
    public String avatar;
//    power: 925
    public long power;
//    rank: 261
    public  long rank;
    public ArrayList<Users> users = new ArrayList<>();

    @Override
    public String toString() {
        return "RankingData{" +
                "response='" + response + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", power=" + power +
                ", rank=" + rank +
                ", users=" + users +
                '}';
    }
}
