package com.kt.ktball.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/14.
 */
public class VideoDetailsData implements Serializable{
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getGame_video_type() {
        return game_video_type;
    }

    public void setGame_video_type(int game_video_type) {
        this.game_video_type = game_video_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJudger() {
        return judger;
    }

    public void setJudger(String judger) {
        this.judger = judger;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }

    public ArrayList<LikeUsers> getLike_users() {
        return like_users;
    }

    public void setLike_users(ArrayList<LikeUsers> like_users) {
        this.like_users = like_users;
    }

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
