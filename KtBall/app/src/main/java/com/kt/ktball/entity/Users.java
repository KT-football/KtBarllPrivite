package com.kt.ktball.entity;

import java.io.Serializable;

/**
 * Created by ARTHUR on 2016/4/12.
 */
public class Users implements Serializable{
//    user_id: 28844
    public long user_id;
//    nickname: 贝云滨
    public String nickname;
//    avatar: /system/user_profiles/avatars/000/027/853/original/0?1459316515
    public String avatar;
//    power: 2780
    public long power;

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", power=" + power +
                '}';
    }
}
