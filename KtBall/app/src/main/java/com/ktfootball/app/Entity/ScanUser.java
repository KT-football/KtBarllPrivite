package com.ktfootball.app.Entity;


import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;

/**
 * Created by ARTHUR on 2016/4/8.
 */
public class ScanUser extends BaseEntity implements Serializable{
//            "ktb": 0,
    public long ktb;
//            "scores": 227,
    public long scores;
//            "nickname": "绮丽",
    public String nickname;
//            "power": 93,
    public long power;
//            "grade": 1,
    public int grade;
//            "user_id": 28885,
    public long user_id;
//            "avatar": "/system/user_profiles/avatars/000/027/894/original/image.jpg?1434003736"
    public String avatar;

    @Override
    public String toString() {
        return "ScanUser{" +
                "response='" + response + '\'' +
                ", ktb=" + ktb +
                ", scores=" + scores +
                ", nickname='" + nickname + '\'' +
                ", power=" + power +
                ", grade=" + grade +
                ", user_id=" + user_id +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
