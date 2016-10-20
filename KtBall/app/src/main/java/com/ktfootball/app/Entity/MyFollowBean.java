package com.ktfootball.app.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 16/10/20.
 */

public class MyFollowBean implements Serializable{

    /**
     * response : success
     * followers : [{"user_id":53938,"nickname":"1450485606","avatar":"/system/user_profiles/avatars/000/053/701/original/0?1449909740","power":1412},{"user_id":57017,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","power":1094},{"user_id":74594,"nickname":"王嘉梁的好伙伴","avatar":"/system/user_profiles/avatars/000/074/346/original/0?1468260849","power":1119}]
     */

    private String response;
    /**
     * user_id : 53938
     * nickname : 1450485606
     * avatar : /system/user_profiles/avatars/000/053/701/original/0?1449909740
     * power : 1412
     */

    private List<FollowersBean> followers;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<FollowersBean> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowersBean> followers) {
        this.followers = followers;
    }

    public static class FollowersBean implements Serializable{
        private long user_id;
        private String nickname;
        private String avatar;
        private int power;

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }
    }
}
