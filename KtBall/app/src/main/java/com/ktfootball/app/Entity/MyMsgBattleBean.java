package com.ktfootball.app.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 16/10/20.
 */

public class MyMsgBattleBean implements Serializable{

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

        private List<BattleInvitationsBean> battle_invitations;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public List<BattleInvitationsBean> getLeague_invitations() {
            return battle_invitations;
        }

        public void setLeague_invitations(List<BattleInvitationsBean> league_invitations) {
            this.battle_invitations = league_invitations;
        }

        public static class BattleInvitationsBean implements Serializable{
            private int battle_invitation_id;
            private int game_id;
            private int user_id;
            private String nickname;
            private String avatar;
            private String game_name;

            public int getBattle_invitation_id() {
                return battle_invitation_id;
            }

            public void setBattle_invitation_id(int battle_invitation_id) {
                this.battle_invitation_id = battle_invitation_id;
            }

            public int getGame_id() {
                return game_id;
            }

            public void setGame_id(int game_id) {
                this.game_id = game_id;
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

            public String getGame_name() {
                return game_name;
            }

            public void setGame_name(String game_name) {
                this.game_name = game_name;
            }
        }
}
