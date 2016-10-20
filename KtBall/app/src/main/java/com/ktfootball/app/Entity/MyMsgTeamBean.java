package com.ktfootball.app.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 16/10/20.
 */

public class MyMsgTeamBean implements Serializable{

    /**
     * response : success
     * league_invitations : [{"league_invitation_id":69,"league_invitation_status":-1,"user_id":57017,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","league_id":3175,"league_name":"吃冒菜不点米饭"}]
     */

    private String response;
    /**
     * league_invitation_id : 69
     * league_invitation_status : -1
     * user_id : 57017
     * nickname : 1475236437
     * avatar : /system/user_profiles/avatars/000/056/783/original/0?1460697065
     * league_id : 3175
     * league_name : 吃冒菜不点米饭
     */

    private List<LeagueInvitationsBean> league_invitations;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<LeagueInvitationsBean> getLeague_invitations() {
        return league_invitations;
    }

    public void setLeague_invitations(List<LeagueInvitationsBean> league_invitations) {
        this.league_invitations = league_invitations;
    }

    public static class LeagueInvitationsBean implements Serializable{
        private int league_invitation_id;
        private int league_invitation_status;
        private int user_id;
        private String nickname;
        private String avatar;
        private int league_id;
        private String league_name;

        public int getLeague_invitation_id() {
            return league_invitation_id;
        }

        public void setLeague_invitation_id(int league_invitation_id) {
            this.league_invitation_id = league_invitation_id;
        }

        public int getLeague_invitation_status() {
            return league_invitation_status;
        }

        public void setLeague_invitation_status(int league_invitation_status) {
            this.league_invitation_status = league_invitation_status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
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

        public int getLeague_id() {
            return league_id;
        }

        public void setLeague_id(int league_id) {
            this.league_id = league_id;
        }

        public String getLeague_name() {
            return league_name;
        }

        public void setLeague_name(String league_name) {
            this.league_name = league_name;
        }
    }
}
