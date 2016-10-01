package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/14.
 */
public class VideoDetailsData {
//    response: success
    public String response;
//    game_video_type: 1
    public int game_video_type;
//    name: 根与芽公益活动
    public String name;
//    local: 申长路988号
    public String local;
//    time: 2016-04-06
    public String time;
//    judger: 姜涛
    public String judger;
//    uri: http://player.youku.com/embed/XMTUyNTQzMDU1Ng==
    public String uri;
//    comment_count: 0
    public int comment_count;
//    likes: 1
    public int likes;
//    users: [ + ]
    public ArrayList<Users> users = new ArrayList<>();
//    like_users: [ + ]
    public ArrayList<LikeUsers> like_users = new ArrayList<>();

    @Override
    public String toString() {
        return "VideoDetailsData{" +
                "response='" + response + '\'' +
                ", game_video_type=" + game_video_type +
                ", name='" + name + '\'' +
                ", local='" + local + '\'' +
                ", time='" + time + '\'' +
                ", judger='" + judger + '\'' +
                ", uri='" + uri + '\'' +
                ", comment_count=" + comment_count +
                ", likes=" + likes +
                ", users=" + users +
                ", like_users=" + like_users +
                '}';
    }
}
