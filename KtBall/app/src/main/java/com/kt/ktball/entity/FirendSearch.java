package com.kt.ktball.entity;

/**
 * Created by ARTHUR on 2016/4/13.
 */
public class FirendSearch {
    //    user_id: 28844
    public long user_id;
    //    nickname: 贝云滨
    public String nickname;
    //    avatar: /system/user_profiles/avatars/000/027/853/original/0?1459316515
    public String avatar;
    //    power: 2780
    public long power;
    public String response;
    public String phone;

    @Override
    public String toString() {
        return "FirendSearch{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", power=" + power +
                ", response='" + response + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
