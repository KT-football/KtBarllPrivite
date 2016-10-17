package com.kt.ktball.entity;

import java.io.Serializable;

/**
 * Created by ARTHUR on 2016/4/14.
 */
public class LikeUsers implements Serializable{
//    user_id: 53934
    public long user_id;
//    nickname: 姜涛
    public String nickname;
//    power: 925
    public long power;

    @Override
    public String toString() {
        return "LikeUsers{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", power=" + power +
                '}';
    }
}
