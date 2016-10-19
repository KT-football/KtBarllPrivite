package com.ktfootball.app.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 16/10/18.
 */

public class MessageBean implements Serializable{

    /**
     * response : success
     * user_messages : [{"user_message_id":567,"type":2,"nickname":"姜涛","avatar":"/system/user_profiles/avatars/000/053/697/original/RackMultipart20160511-12415-h18gf2?1462958628","content":"在姜涛 vs canj的比赛中评论了你的影片","game_video_id":14513,"is_read":1},{"user_message_id":564,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14535,"is_read":1},{"user_message_id":560,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14527,"is_read":1},{"user_message_id":558,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14515,"is_read":1},{"user_message_id":556,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14535,"is_read":1},{"user_message_id":554,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14369,"is_read":0},{"user_message_id":552,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14301,"is_read":0},{"user_message_id":550,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14303,"is_read":0},{"user_message_id":549,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14351,"is_read":1},{"user_message_id":547,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14513,"is_read":0},{"user_message_id":545,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14513,"is_read":0},{"user_message_id":542,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14303,"is_read":0},{"user_message_id":541,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14351,"is_read":0},{"user_message_id":538,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14393,"is_read":0},{"user_message_id":536,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14527,"is_read":0},{"user_message_id":534,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14515,"is_read":0},{"user_message_id":532,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14516,"is_read":0},{"user_message_id":530,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14527,"is_read":0},{"user_message_id":528,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14527,"is_read":0},{"user_message_id":526,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14535,"is_read":0},{"user_message_id":522,"type":1,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"评论了你的影片","game_video_id":14515,"is_read":0},{"user_message_id":519,"type":2,"nickname":"姜涛","avatar":"/system/user_profiles/avatars/000/053/697/original/RackMultipart20160511-12415-h18gf2?1462958628","content":"在步雨涵 vs 余旸的比赛的评论中跟随了评论","game_video_id":13455,"is_read":0},{"user_message_id":518,"type":2,"nickname":"姜涛","avatar":"/system/user_profiles/avatars/000/053/697/original/RackMultipart20160511-12415-h18gf2?1462958628","content":"在步雨涵 vs 余旸的比赛的评论中跟随了评论","game_video_id":13455,"is_read":0},{"user_message_id":512,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在步雨涵 vs 余旸的比赛的评论中跟随了评论","game_video_id":13455,"is_read":0},{"user_message_id":511,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在步雨涵 vs 余旸的比赛的评论中跟随了评论","game_video_id":13455,"is_read":0},{"user_message_id":510,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在步雨涵 vs 余旸的比赛的评论中跟随了评论","game_video_id":13455,"is_read":0},{"user_message_id":495,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在闫子轩 vs 朱梦泽的比赛的评论中跟随了评论","game_video_id":13006,"is_read":0},{"user_message_id":484,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在步雨涵 vs 余旸的比赛的评论中跟随了评论","game_video_id":13455,"is_read":1},{"user_message_id":469,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在闫子轩 vs 朱梦泽的比赛的评论中跟随了评论","game_video_id":13006,"is_read":0},{"user_message_id":466,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在闫子轩 vs 朱梦泽的比赛的评论中跟随了评论","game_video_id":13006,"is_read":0},{"user_message_id":463,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在popeye vs 王小明找的比赛的评论中跟随了评论","game_video_id":10817,"is_read":1},{"user_message_id":444,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在popeye vs 王小明找的比赛的评论中跟随了评论","game_video_id":10817,"is_read":0},{"user_message_id":441,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在popeye vs 王小明找的比赛的评论中跟随了评论","game_video_id":10817,"is_read":0},{"user_message_id":440,"type":2,"nickname":"1475236437","avatar":"/system/user_profiles/avatars/000/056/783/original/0?1460697065","content":"在popeye vs 王小明找的比赛的评论中跟随了评论","game_video_id":10817,"is_read":0},{"user_message_id":423,"type":2,"nickname":"姜涛","avatar":"/system/user_profiles/avatars/000/053/697/original/RackMultipart20160511-12415-h18gf2?1462958628","content":"在闫子轩 vs 朱梦泽的比赛的评论中跟随了评论","game_video_id":13006,"is_read":1}]
     */

    private String response;
    /**
     * user_message_id : 567
     * type : 2
     * nickname : 姜涛
     * avatar : /system/user_profiles/avatars/000/053/697/original/RackMultipart20160511-12415-h18gf2?1462958628
     * content : 在姜涛 vs canj的比赛中评论了你的影片
     * game_video_id : 14513
     * is_read : 1
     */

    private List<UserMessagesBean> user_messages;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<UserMessagesBean> getUser_messages() {
        return user_messages;
    }

    public void setUser_messages(List<UserMessagesBean> user_messages) {
        this.user_messages = user_messages;
    }

    public static class UserMessagesBean implements Serializable{
        private int user_message_id;
        private int type;
        private String nickname;
        private String avatar;
        private String content;
        private int game_video_id;
        private int is_read;

        public int getUser_message_id() {
            return user_message_id;
        }

        public void setUser_message_id(int user_message_id) {
            this.user_message_id = user_message_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getGame_video_id() {
            return game_video_id;
        }

        public void setGame_video_id(int game_video_id) {
            this.game_video_id = game_video_id;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }
    }
}
