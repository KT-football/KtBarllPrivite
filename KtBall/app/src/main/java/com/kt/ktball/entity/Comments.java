package com.kt.ktball.entity;

/**
 * Created by ARTHUR on 2016/4/14.
 */
public class Comments {
//    game_video_comment_id: 163
    public long game_video_comment_id;
//    nickname: 贝云滨
    public String nickname;
//    avatar: /system/user_profiles/avatars/000/027/853/original/0?1459316515
    public String avatar;
//    content: lalal
    public String content;
//    can_delete: 1
    public int can_delete;

    @Override
    public String toString() {
        return "Comments{" +
                "game_video_comment_id=" + game_video_comment_id +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", can_delete=" + can_delete +
                '}';
    }
}
