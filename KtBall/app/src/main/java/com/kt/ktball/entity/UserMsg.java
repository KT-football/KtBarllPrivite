package com.kt.ktball.entity;

/**
 * Created by ww on 2016/3/25.
 */
public class UserMsg {

//    response: success
    public String response;
//    user_id: 53934
    public long user_id;
//    avatar: /system/user_profiles/avatars/000/053/697/original/RackMultipart20160304-21531-1n5sevw?1457087237
    public String avatar;
//    nickname: 姜涛
    public String nickname;
//    power: 958
    public long power;
//    gender: 男
    public String gender;
//    rank: 233
    public long rank;
//    level: 0
    public int level;
//    exp: 132
    public int exp;
//    win: 7
    public int win;
//    draw: 1
    public int draw;
//    lose: 4
    public int lose;
//    win_rate: 58.33
    public double win_rate;
//    goals: 2.67
    public double goals;
//    pannas: 0.33
    public double pannas;
//    kt: 0.17
    public double kt;
//    scores: 5.67
    public double scores;
//    0 或 1(0 未关注,1 已关注)
    public int followed;

    @Override
    public String toString() {
        return "UserMsg{" +
                "response='" + response + '\'' +
                ", user_id=" + user_id +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", power=" + power +
                ", gender='" + gender + '\'' +
                ", rank=" + rank +
                ", level=" + level +
                ", exp=" + exp +
                ", win=" + win +
                ", draw=" + draw +
                ", lose=" + lose +
                ", win_rate=" + win_rate +
                ", goals=" + goals +
                ", pannas=" + pannas +
                ", kt=" + kt +
                ", scores=" + scores +
                ", followed=" + followed +
                '}';
    }
}
