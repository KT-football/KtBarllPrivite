package com.kt.ktball.entity;

/**
 * Created by ARTHUR on 2016/4/15.
 */
public class TeamDetailData {
//    response: "success",
    public String response;
// name: "战队名称",
    public String name;
// avatar: "战队头像url",
    public String avatar;
// game_type: 1或2(1: 2v2,2: 3v3),
    public int game_type;
// grade: 3(战队等级),
    public int grade;
// scores: 10(战队积分),
    public int scores;
//    usera_id: 用户1 ID ,
    public long usera_id;
// usera_avatar: "队员1头像",
    public String usera_avatar;
// usera_nickname: "队员1昵称",
    public String usera_nickname;
//    userb_id: 用户2 ID,
    public long userb_id;
// userb_avatar: "队员2头像",
    public String userb_avatar;
// userb_nickname: "队员b昵称",
    public String userb_nickname;
//    userc_id: 用户3 ID,
    public long userc_id;
// userc_avatar: "队员3头像",
    public String userc_avatar;
// userc_nickname: "队员3昵称",
    public String userc_nickname;
//    win_rate: "45%"(胜率),
    public String win_rate;
// kt_count: 12(总KT数),
    public int kt_count;
// goal_count: 10(进球平均),
    public String goal_count;
// pannas_count: 10 (穿裆平均)
    public int pannas_count;
    public int usera_power;
    public int userb_power;
    public int userc_power;

}
