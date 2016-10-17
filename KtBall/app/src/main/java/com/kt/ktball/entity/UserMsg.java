package com.kt.ktball.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ww on 2016/3/25.
 */
public class UserMsg implements Serializable{


    /**
     * response : success
     * user_id : 3
     * avatar : /system/user_profiles/avatars/000/000/008/original/0?1456748421
     * nickname : 刘力豪leokt足球
     * power : 2044
     * gender : 女
     * rank : 3
     * level : 3
     * exp : 2690
     * win : 251
     * draw : 55
     * lose : 91
     * win_rate : 60.77
     * goals : 2.1
     * pannas : 0.97
     * kt : 0.18
     * scores : 5.17
     * total_goals : 868
     * total_pannas : 401
     * fans_count : 25
     * follow_count : 7
     * last10_goals_list : [0,0,1,3,1,1,1,2,1,1]
     * last10_pannas_list : [0,1,2,1,0,1,2,5,1,1]
     * last10_game_video_id_list : [14304,14299,14204,14123,14122,14121]
     */

    public String response;
    public int user_id;
    public String avatar;
    public String nickname;
    public int power;
    public String gender;
    public int rank;
    public int level;
    public int exp;
    public int win;
    public int draw;
    public int lose;
    public double win_rate;
    public double goals;
    public double pannas;
    public double kt;
    public double scores;
    public int total_goals;
    public int total_pannas;
    public int fans_count;
    public int follow_count;
    public List<Integer> last10_goals_list;
    public List<Integer> last10_pannas_list;
    public List<Integer> last10_game_video_id_list;
    public int followed = 0;

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public double getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(double win_rate) {
        this.win_rate = win_rate;
    }

    public double getGoals() {
        return goals;
    }

    public void setGoals(double goals) {
        this.goals = goals;
    }

    public double getPannas() {
        return pannas;
    }

    public void setPannas(double pannas) {
        this.pannas = pannas;
    }

    public double getKt() {
        return kt;
    }

    public void setKt(double kt) {
        this.kt = kt;
    }

    public double getScores() {
        return scores;
    }

    public void setScores(double scores) {
        this.scores = scores;
    }

    public int getTotal_goals() {
        return total_goals;
    }

    public void setTotal_goals(int total_goals) {
        this.total_goals = total_goals;
    }

    public int getTotal_pannas() {
        return total_pannas;
    }

    public void setTotal_pannas(int total_pannas) {
        this.total_pannas = total_pannas;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public List<Integer> getLast10_goals_list() {
        return last10_goals_list;
    }

    public void setLast10_goals_list(List<Integer> last10_goals_list) {
        this.last10_goals_list = last10_goals_list;
    }

    public List<Integer> getLast10_pannas_list() {
        return last10_pannas_list;
    }

    public void setLast10_pannas_list(List<Integer> last10_pannas_list) {
        this.last10_pannas_list = last10_pannas_list;
    }

    public List<Integer> getLast10_game_video_id_list() {
        return last10_game_video_id_list;
    }

    public void setLast10_game_video_id_list(List<Integer> last10_game_video_id_list) {
        this.last10_game_video_id_list = last10_game_video_id_list;
    }
}
