package com.ktfootball.app.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 16/10/20.
 */

public class MyFasnBean implements Serializable{

    /**
     * response : success
     * fans : [{"user_id":28844,"nickname":"贝云滨","avatar":"/system/user_profiles/avatars/000/027/853/original/0?1473151195","power":1560},{"user_id":53934,"nickname":"姜涛","avatar":"/system/user_profiles/avatars/000/053/697/original/RackMultipart20160511-12415-h18gf2?1462958628","power":1058},{"user_id":57017,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","power":1094},{"user_id":74600,"nickname":"\u200d呼呼 ","avatar":"/system/user_profiles/avatars/000/074/352/original/RackMultipart20160810-13963-1cchv1x?1470800235","power":1725}]
     */

    private String response;
    /**
     * user_id : 28844
     * nickname : 贝云滨
     * avatar : /system/user_profiles/avatars/000/027/853/original/0?1473151195
     * power : 1560
     */

    private List<FansBean> fans;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<FansBean> getFans() {
        return fans;
    }

    public void setFans(List<FansBean> fans) {
        this.fans = fans;
    }

    public static class FansBean implements Serializable{
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
