package com.kt.ktball.entity;

/**
 * Created by E7240A on 2016-04-13.
 */
public class User {
    private int user_id;
    private String nickname;
    private int grade;
    private String avatar;
    private int scores;

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", grade=" + grade +
                ", avatar='" + avatar + '\'' +
                ", scores=" + scores +
                '}';
    }
}
