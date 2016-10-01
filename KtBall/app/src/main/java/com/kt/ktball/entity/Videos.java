package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by E7240A on 2016-04-12.
 */
public class Videos {

    private int game_video_id;
    private int game_video_type;
    private String scores;
    private String time;
    private String local;
    private ArrayList<User> users;
    private String uri;
    private String screenshot;
    private String video_time;
    public String picture;//: 比赛图片

    public int getGame_video_id() {
        return game_video_id;
    }

    public void setGame_video_id(int game_video_id) {
        this.game_video_id = game_video_id;
    }

    public int getGame_video_type() {
        return game_video_type;
    }

    public void setGame_video_type(int game_video_type) {
        this.game_video_type = game_video_type;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVideo_time() {
        return video_time;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "game_video_id=" + game_video_id +
                ", game_video_type=" + game_video_type +
                ", scores='" + scores + '\'' +
                ", time='" + time + '\'' +
                ", local='" + local + '\'' +
                ", users=" + users +
                ", uri='" + uri + '\'' +
                ", screenshot='" + screenshot + '\'' +
                ", video_time='" + video_time + '\'' +
                '}';
    }
}
